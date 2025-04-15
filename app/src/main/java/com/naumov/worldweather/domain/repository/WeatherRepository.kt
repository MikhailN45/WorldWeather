package com.naumov.worldweather.domain.repository

import com.naumov.worldweather.domain.model.util.Result
import com.naumov.worldweather.domain.model.weather.WeatherInfo
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun getWeatherDataFromApi(lat: Double, long: Double): Result<WeatherInfo>
    fun fetchWeatherFlow(): Flow<WeatherInfo?>
}