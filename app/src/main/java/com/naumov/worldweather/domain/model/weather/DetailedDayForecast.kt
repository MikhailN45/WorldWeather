package com.naumov.worldweather.domain.model.weather

data class DetailedDayForecast(
    val date: String = "",
    val weatherTypeMorning: WeatherType,
    val weatherTypeDay: WeatherType,
    val weatherTypeEvening: WeatherType,
    val weatherTypeNight: WeatherType,
    val temperatureMorning: Int,
    val temperatureDay: Int,
    val temperatureEvening: Int,
    val temperatureNight: Int,
    val feelTemperatureMorning: Int,
    val feelTemperatureDay: Int,
    val feelTemperatureEvening: Int,
    val feelTemperatureNight: Int,
    val windMorning: Int,
    val windDay: Int,
    val windEvening: Int,
    val windNight: Int,
    val humidityMorning: Int,
    val humidityDay: Int,
    val humidityEvening: Int,
    val humidityNight: Int,
    val pressureMorning: Int,
    val pressureDay: Int,
    val pressureEvening: Int,
    val pressureNight: Int
)