package com.naumov.worldweather.data.repository

import com.naumov.worldweather.data.database.dao.WeatherDao
import com.naumov.worldweather.data.mappers.toWeatherEntity
import com.naumov.worldweather.data.mappers.toWeatherInfo
import com.naumov.worldweather.data.remote.WeatherApi
import com.naumov.worldweather.domain.repository.WeatherRepository
import com.naumov.worldweather.domain.model.util.Result
import com.naumov.worldweather.domain.model.weather.WeatherInfo
import com.naumov.worldweather.domain.preferences.PreferencesManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.net.UnknownHostException
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi,
    private val weatherDao: WeatherDao,
    private val preferencesManager: PreferencesManager
) : WeatherRepository {
    override suspend fun getWeatherDataFromApi(lat: Double, lon: Double): Result<WeatherInfo> {
        return try {
            val weatherInfo = api.getWeatherData(lat, lon).toWeatherInfo() //Net -> Domain
            saveWeatherDataToDb(weatherInfo)
            preferencesManager.saveLastUpdateTime(System.currentTimeMillis())
            Result.Success(data = weatherInfo)
        } catch (e: UnknownHostException) {
            e.printStackTrace()
            Result.Error("Нет подключения к интернету. Проверьте сеть и повторите попытку.")
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(e.message ?: "Unknown NET REQUEST error!")
        }
    }

    private suspend fun saveWeatherDataToDb(weatherInfo: WeatherInfo) {
        val (weatherEntity, hourlyWeatherDataEntities) = weatherInfo.toWeatherEntity() //Domain -> Data
        val weatherEntityId = weatherDao.insertWeather(weatherEntity) //generate ID
        val hourlyWeatherDataEntitiesWithId = hourlyWeatherDataEntities.map {
            it.copy(weatherInfoId = weatherEntityId.toInt())
        } //link weather by hours to ID
        weatherDao.insertHourlyWeatherData(hourlyWeatherDataEntitiesWithId) //save linked data
    }

    override fun fetchWeatherFlow(): Flow<WeatherInfo?> {
        return weatherDao.getLastWeatherFlow()
            .map { weatherEntity ->
                weatherEntity?.let { entity ->
                    val hourlyWeatherData = weatherDao.getHourlyWeatherDataByWeatherId(entity.id)
                    entity.toWeatherInfo(hourlyWeatherData)
                }
            }
    }
}