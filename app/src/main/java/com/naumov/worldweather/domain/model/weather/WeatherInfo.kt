package com.naumov.worldweather.domain.model.weather

data class WeatherInfo(
    val weatherDataPerDay: Map<Int, List<DayWeatherData>>,
    val currentDayWeatherData: DayWeatherData?
)