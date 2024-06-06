package com.naumov.worldweather.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naumov.worldweather.domain.location.LocationTracker
import com.naumov.worldweather.domain.repository.WeatherRepository
import com.naumov.worldweather.domain.util.Resource
import com.naumov.worldweather.domain.weather.DayWeatherData
import com.naumov.worldweather.domain.weather.WeeklyForecast
import com.naumov.worldweather.presentation.event.Event
import com.naumov.worldweather.presentation.state.WeatherState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val locationTracker: LocationTracker
) : ViewModel() {
    private val failToGetLocationMessage =
        "Fail to get current location, make sure that GPS is active and permissions are granted!"
    private val formatterDayMonth: DateTimeFormatter =
        DateTimeFormatter.ofPattern("d MMMM", Locale("ru"))
    private val _state: MutableLiveData<WeatherState> = MutableLiveData(WeatherState())
    val state: LiveData<WeatherState> = _state

    fun processEvent(event: Event) {
        when (event) {
            is Event.RefreshData -> loadWeatherInfo()
            is Event.GetHourlyForecast -> getDailyForecast(event.state)
            is Event.GetWeeklyForecast -> getWeeklyForecast(event.state)
        }
    }

    private fun loadWeatherInfo() {
        viewModelScope.launch {
            _state.value = state.value?.copy(
                isLoading = true,
                error = null
            )

            locationTracker.getCurrentLocation()?.let { location ->
                val result =
                    repository.getWeatherData(location.latitude, location.longitude)
                when (result) {
                    is Resource.Success -> {
                        _state.value = state.value?.copy(
                            weatherInfo = result.data,
                            location = location,
                            isLoading = false,
                            error = null
                        )
                    }

                    is Resource.Error -> {
                        _state.value = state.value?.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            } ?: kotlin.run {
                _state.value = state.value?.copy(
                    isLoading = false,
                    error = failToGetLocationMessage
                )
            }
        }
    }

    fun getWeeklyForecast(state: WeatherState): List<WeeklyForecast> =
        state.weatherInfo?.weatherDataPerDay?.values?.map { list ->
            WeeklyForecast(
                date = list.first().time.toLocalDate().format(formatterDayMonth),
                dayTemperature = list.first { it.time.hour == 15 }.temperatureCelsius,
                nightTemperature = list.first { it.time.hour == 3 }.temperatureCelsius,
                weatherType = list.first().weatherType
            )
        } ?: emptyList()


    fun getDailyForecast(state: WeatherState): List<DayWeatherData> {
        val nowHour = LocalDateTime.now().hour
        val dayWeatherDataPerDay = state.weatherInfo?.weatherDataPerDay
        val todayWeather = dayWeatherDataPerDay?.get(0)?.drop(nowHour)
        val tomorrowWeather = dayWeatherDataPerDay?.get(1)?.take(nowHour)
        return (todayWeather ?: emptyList()) + (tomorrowWeather ?: emptyList())
    }
}