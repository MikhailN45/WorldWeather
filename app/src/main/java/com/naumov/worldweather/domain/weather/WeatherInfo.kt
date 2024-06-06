package com.naumov.worldweather.domain.weather

data class WeatherInfo(
    val dayWeatherDataPerDay: Map<Int, List<DayWeatherData>>,
    val currentDayWeatherData: DayWeatherData?
)