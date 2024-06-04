package com.naumov.worldweather.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naumov.worldweather.domain.location.LocationTracker
import com.naumov.worldweather.domain.repository.WeatherRepository
import com.naumov.worldweather.domain.util.Resource
import com.naumov.worldweather.presentation.state.WeatherState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val locationTracker: LocationTracker
) : ViewModel() {

    private val failToGetLocationMessage =
        "Fail to get current location, make sure that GPS is active and permissions are granted!"
    private val _state: MutableLiveData<WeatherState> = MutableLiveData(WeatherState())
    val state: LiveData<WeatherState> = _state

    fun loadWeatherInfo() {
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
}