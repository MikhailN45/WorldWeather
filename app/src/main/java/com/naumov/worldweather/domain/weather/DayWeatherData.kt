package com.naumov.worldweather.domain.weather

import java.time.LocalDateTime

data class DayWeatherData(
    val time: LocalDateTime,
    val temperature: Int,
    val humidity: Int,
    val feelsTemperature: Int,
    val pressure: Int,
    val windSpeed: Int,
    val weatherType: WeatherType
)
