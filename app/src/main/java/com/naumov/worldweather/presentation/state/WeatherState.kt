package com.naumov.worldweather.presentation.state

import android.location.Location
import com.naumov.worldweather.domain.weather.DayWeatherData
import com.naumov.worldweather.domain.weather.DetailedDayForecast
import com.naumov.worldweather.domain.weather.WeatherInfo
import com.naumov.worldweather.domain.weather.WeeklyForecast

data class WeatherState(
    val weatherInfo: WeatherInfo? = null,
    val weeklyForecast: List<WeeklyForecast> = emptyList(),
    val hourlyForecast: List<DayWeatherData> = emptyList(),
    val detailedDayForecast: DetailedDayForecast? = null,
    val location: Location? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
