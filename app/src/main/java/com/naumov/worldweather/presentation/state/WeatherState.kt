package com.naumov.worldweather.presentation.state

import android.location.Location
import com.naumov.worldweather.domain.weather.WeatherInfo
import com.naumov.worldweather.domain.weather.WeeklyForecast

data class WeatherState(
    val weatherInfo: WeatherInfo? = null,
    val weeklyForecastList: List<WeeklyForecast> = emptyList(),
    val location: Location? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
