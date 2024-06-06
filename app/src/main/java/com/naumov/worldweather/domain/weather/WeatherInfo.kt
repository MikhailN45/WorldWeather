package com.naumov.worldweather.domain.weather

data class WeatherInfo(
    val weatherDataPerDay: Map<Int, List<DayWeatherData>>,
    val currentDayWeatherData: DayWeatherData?
)