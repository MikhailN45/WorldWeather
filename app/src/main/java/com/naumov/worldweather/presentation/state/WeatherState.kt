package com.naumov.worldweather.presentation.state

import android.location.Location
import com.naumov.worldweather.domain.weather.WeatherInfo

data class WeatherState(
    val weatherInfo: WeatherInfo? = null,
    val location: Location? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
