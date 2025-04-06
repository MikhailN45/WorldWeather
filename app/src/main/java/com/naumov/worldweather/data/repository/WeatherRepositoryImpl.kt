package com.naumov.worldweather.data.repository

import com.naumov.worldweather.data.database.dao.WeatherDao
import com.naumov.worldweather.data.mappers.toWeatherEntity
import com.naumov.worldweather.data.mappers.toWeatherInfo
import com.naumov.worldweather.data.remote.WeatherApi
import com.naumov.worldweather.domain.repository.WeatherRepository
import com.naumov.worldweather.domain.model.util.Result
import com.naumov.worldweather.domain.model.weather.WeatherInfo
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi,
    private val weatherDao: WeatherDao
) : WeatherRepository {

    override suspend fun getWeatherData(lat: Double, lon: Double): Result<WeatherInfo> {
        return try {
            val weatherInfo = api.getWeatherData(lat, lon).toWeatherInfo() //Net -> Domain

            val (weatherEntity, hourlyWeatherDataEntities) = weatherInfo.toWeatherEntity() //Domain -> Data

            val weatherEntityId = weatherDao.insertWeather(weatherEntity) //generate ID
            val hourlyWeatherDataEntitiesWithId = hourlyWeatherDataEntities.map {
                it.copy(weatherInfoId = weatherEntityId.toInt())
            } //link weather by hours to ID
            weatherDao.insertHourlyWeatherData(hourlyWeatherDataEntitiesWithId) //save linked data

            Result.Success(data = weatherInfo)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(e.message ?: "Unknown error!")
        }
    }
}