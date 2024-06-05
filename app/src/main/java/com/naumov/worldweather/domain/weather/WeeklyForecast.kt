package com.naumov.worldweather.domain.weather

data class WeeklyForecast(
    val date: String,
    val dayTemperature: Double,
    val nightTemperature: Double,
    val weatherType: WeatherType
)
