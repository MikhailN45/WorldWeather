package com.naumov.worldweather.presentation.state

import android.location.Location
import com.naumov.worldweather.domain.model.weather.DayWeatherData
import com.naumov.worldweather.domain.model.weather.DetailedDayForecast
import com.naumov.worldweather.domain.model.weather.WeatherInfo
import com.naumov.worldweather.domain.model.weather.WeatherType
import com.naumov.worldweather.domain.model.weather.WeeklyForecast
import java.time.LocalDateTime

data class WeatherState(
    val weatherInfo: WeatherInfo? = emptyWeatherInfo,
    val weeklyForecast: List<WeeklyForecast> = emptyList(),
    val hourlyForecast: List<DayWeatherData> = emptyList(),
    val detailedDayForecast: DetailedDayForecast? = null,
    val location: Location? = null,
    val locationName: String = "",
    val lastUpdateTime: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)

internal val emptyWeatherInfo = WeatherInfo(
    weatherDataPerDay = emptyMap(),
    currentDayWeatherData = DayWeatherData(
        time = LocalDateTime.now(),
        temperature = 0,
        humidity = 0,
        feelsTemperature = 0,
        pressure = 0,
        windSpeed = 0,
        weatherType = WeatherType.ClearSky
    )
)
