package com.naumov.worldweather.domain.repository

import com.naumov.worldweather.domain.util.Resource
import com.naumov.worldweather.domain.weather.WeatherInfo

interface WeatherRepository {
    suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo>
}