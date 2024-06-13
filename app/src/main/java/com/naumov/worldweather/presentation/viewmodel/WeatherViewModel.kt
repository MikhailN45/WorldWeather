package com.naumov.worldweather.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naumov.worldweather.domain.location.LocationTracker
import com.naumov.worldweather.domain.model.util.Result
import com.naumov.worldweather.domain.model.weather.DayWeatherData
import com.naumov.worldweather.domain.model.weather.DetailedDayForecast
import com.naumov.worldweather.domain.model.weather.WeatherInfo
import com.naumov.worldweather.domain.model.weather.WeatherType
import com.naumov.worldweather.domain.model.weather.WeeklyForecast
import com.naumov.worldweather.domain.repository.WeatherRepository
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
    private val _state: MutableLiveData<WeatherState> = MutableLiveData(WeatherState())
    val state: LiveData<WeatherState> = _state

    fun processEvent(event: Event) {
        when (event) {
            is Event.RefreshData -> loadWeatherInfo()
            is Event.WeeklyForecastDayPressed -> getDetailedDayForecast(event.dayIndex)
        }
    }


    private fun loadWeatherInfo() {
        viewModelScope.launch {
            _state.value = state.value?.copy(isLoading = true)
            val location = locationTracker.getCurrentLocation() ?: run {
                _state.value = state.value?.copy(
                    isLoading = false,
                    error = FAIL_TO_GET_LOC_ERR
                )
                return@launch
            }
            when (val weatherInfoResult =
                repository.getWeatherData(location.latitude, location.longitude)) {
                is Result.Success -> {
                    _state.value = state.value?.copy(
                        weatherInfo = weatherInfoResult.data,
                        location = location,
                        weeklyForecast = weatherInfoResult.data?.let { getWeeklyForecast(it) }
                            .orEmpty(),
                        hourlyForecast = weatherInfoResult.data?.let { getTodayHourlyForecast(it) }
                            .orEmpty(),
                        isLoading = false,
                        error = null
                    )
                }

                is Result.Error -> {
                    _state.value = state.value?.copy(
                        isLoading = false,
                        error = weatherInfoResult.message
                    )
                }
            }
        }
    }

    private fun getWeeklyForecast(weatherInfo: WeatherInfo): List<WeeklyForecast> =
        weatherInfo.weatherDataPerDay.values.map { dayWeather ->
            val dayTemperature =
                countAverage(dayWeather.filter { it.time.hour in 11..17 }) { it.temperature }
            val nightTemperature =
                countAverage(dayWeather.filter { it.time.hour in 0..6 }) { it.temperature }
            val weatherType = calculateCommonWeatherType(dayWeather) { it.weatherType }

            WeeklyForecast(
                date = dayWeather.first().time.toLocalDate().format(formatterDayMonth),
                dayTemperature = dayTemperature,
                nightTemperature = nightTemperature,
                weatherType = weatherType
            )
        }


    private fun getTodayHourlyForecast(weatherInfo: WeatherInfo): List<DayWeatherData> {
        val nowHour = LocalDateTime.now().hour
        val weatherDataPerDay = weatherInfo.weatherDataPerDay
        val todayWeather = weatherDataPerDay[0]?.drop(nowHour)
        val tomorrowWeather = weatherDataPerDay[1]?.take(nowHour)
        return (todayWeather ?: emptyList()) + (tomorrowWeather ?: emptyList())
    }

    private fun getDetailedDayForecast(dayIndex: Int) {
        val selectedDayWeather =
            state.value?.weatherInfo?.weatherDataPerDay?.get(dayIndex) ?: emptyList()

        val (nightData,
            morningData,
            dayData,
            eveningData
        ) = selectedDayWeather.chunked(selectedDayWeather.size / 4)

        val periods = listOf(nightData, morningData, dayData, eveningData)
        val date = selectedDayWeather.first().time.format(formatterDayMonth)

        val weatherTypes = periods.map { period ->
            calculateCommonWeatherType(period) { it.weatherType }
        }
        val temperatures = periods.map { period ->
            countAverage(period) { it.temperature }
        }
        val feelsTemperatures = periods.map { period ->
            countAverage(period) { it.feelsTemperature }
        }
        val winds = periods.map { period ->
            countAverage(period) { it.windSpeed }
        }
        val humidities = periods.map { period ->
            countAverage(period) { it.humidity }
        }
        val pressures = periods.map { period ->
            countAverage(period) { it.pressure }
        }

        val detailedDayForecast = DetailedDayForecast(
            date = date,
            weatherTypeNight = weatherTypes[0],
            weatherTypeMorning = weatherTypes[1],
            weatherTypeDay = weatherTypes[2],
            weatherTypeEvening = weatherTypes[3],

            temperatureNight = temperatures[0],
            temperatureMorning = temperatures[1],
            temperatureDay = temperatures[2],
            temperatureEvening = temperatures[3],

            feelTemperatureNight = feelsTemperatures[0],
            feelTemperatureMorning = feelsTemperatures[1],
            feelTemperatureDay = feelsTemperatures[2],
            feelTemperatureEvening = feelsTemperatures[3],

            windNight = winds[0],
            windMorning = winds[1],
            windDay = winds[2],
            windEvening = winds[3],

            humidityNight = humidities[0],
            humidityMorning = humidities[1],
            humidityDay = humidities[2],
            humidityEvening = humidities[3],

            pressureNight = pressures[0],
            pressureMorning = pressures[1],
            pressureDay = pressures[2],
            pressureEvening = pressures[3]
        )

        _state.value = state.value?.copy(
            detailedDayForecast = detailedDayForecast
        )
    }

    private fun countAverage(
        data: List<DayWeatherData>,
        selector: (DayWeatherData) -> Int
    ): Int {
        return data.map(selector).average().toInt()
    }

    private fun calculateCommonWeatherType(
        data: List<DayWeatherData>,
        selector: (DayWeatherData) -> WeatherType
    ): WeatherType {
        return data.groupingBy(selector).eachCount().maxByOrNull { it.value }?.key
            ?: WeatherType.ClearSky
    }

    companion object {
        private val formatterDayMonth: DateTimeFormatter =
            DateTimeFormatter.ofPattern("d MMMM", Locale("ru"))
        private const val FAIL_TO_GET_LOC_ERR =
            "Fail to get current location, make sure that GPS is active and permissions are granted!"
    }
}