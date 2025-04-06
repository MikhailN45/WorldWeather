package com.naumov.worldweather.data.repository

import com.naumov.worldweather.data.database.AppDatabase
import com.naumov.worldweather.data.database.dao.WeatherDao
import com.naumov.worldweather.data.mappers.toWeatherInfo
import com.naumov.worldweather.data.remote.WeatherApi
import com.naumov.worldweather.domain.repository.WeatherRepository
import com.naumov.worldweather.domain.model.util.Result
import com.naumov.worldweather.domain.model.weather.WeatherInfo
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi,
    //private val database: AppDatabase,
    //private val weatherDao: WeatherDao
) : WeatherRepository {

    override suspend fun getWeatherData(lat: Double, long: Double): Result<WeatherInfo> {
        return try {
            Result.Success(
                data = api.getWeatherData(
                    lat = lat,
                    long = long
                ).toWeatherInfo()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(e.message ?: "Unknown error!")
        }
    }
}