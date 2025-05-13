package com.naumov.worldweather.presentation.state

import android.location.Location
import com.naumov.worldweather.domain.model.weather.DayWeatherData
import com.naumov.worldweather.domain.model.weather.DetailedDayForecast
import com.naumov.worldweather.domain.model.weather.WeatherInfo
import com.naumov.worldweather.domain.model.weather.WeeklyForecast

data class WeatherState(
    val weatherInfo: WeatherInfo? = null,
    val weeklyForecast: List<WeeklyForecast> = emptyList(),
    val hourlyForecast: List<DayWeatherData> = emptyList(),
    val detailedDayForecast: DetailedDayForecast? = null,
    val location: Location? = null,
    val locationName: String = "",
    val lastUpdateTime: String = "",
    val isLoading: Boolean = true,
    val isStateFilledSuccessfully: Boolean = false,
    val error: String? = null
)