package com.naumov.worldweather.domain.model.weather

data class WeeklyForecast(
    val date: String,
    val dayTemperature: Int,
    val nightTemperature: Int,
    val weatherType: WeatherType
)
