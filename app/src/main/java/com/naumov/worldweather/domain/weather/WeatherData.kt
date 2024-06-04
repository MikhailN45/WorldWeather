package com.naumov.worldweather.domain.weather

import java.time.LocalDateTime

data class WeatherData(
    val time: LocalDateTime,
    val temperatureCelsius: Double,
    val humidity: Double,
    val apparentTemperature: Double,
    val pressure: Double,
    val windSpeed: Double,
    val weatherType: WeatherType
)
