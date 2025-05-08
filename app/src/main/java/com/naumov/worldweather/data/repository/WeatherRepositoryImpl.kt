package com.naumov.worldweather.data.repository

import com.naumov.worldweather.data.database.dao.WeatherDao
import com.naumov.worldweather.data.database.dao.WeatherEntity
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
    private val api: WeatherApi, //remoteDS
    private val weatherDao: WeatherDao, //datasource catch errors  LocalDataSource / RoomLocalDataSource
    private val preferencesManager: PreferencesManager
) : WeatherRepository {
    override suspend fun getWeatherDataFromApi(lat: Double, lon: Double): Result<WeatherInfo> {
        return try {
            val weatherInfo = api.getWeatherData(lat, lon).toWeatherInfo() //Net -> Domain
            updateDatabase(weatherInfo)
            preferencesManager.saveLastUpdateTime(System.currentTimeMillis())
            Result.Success(data = weatherInfo)
        } catch (e: UnknownHostException) {
            e.printStackTrace()
            Result.Error(NO_CONNECTION_ERR)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(e.message ?: UNKNOWN_ERR)
        }
    }

    private suspend fun updateDatabase(weatherInfo: WeatherInfo) {
        weatherDao.deleteOldWeatherData()

        val (weatherEntity, hourlyWeatherDataEntities) = weatherInfo.toWeatherEntity() //Domain -> Data || Convert in Repo
        val weatherEntityId = weatherDao.insertWeather(weatherEntity) //generate ID DS -
        val hourlyWeatherDataEntitiesWithId = hourlyWeatherDataEntities.map {
            it.copy(weatherInfoId = weatherEntityId.toInt())
        } //link weather by hours to ID

        weatherDao.insertHourlyWeatherData(hourlyWeatherDataEntitiesWithId) //save linked data
    }

    override fun fetchWeatherFlow(): Flow<WeatherInfo?> {
        return weatherDao.getLastWeatherFlow()
            .map { weatherEntity ->
                weatherEntity?.let { transformWeatherEntity(it) }
            }
    }

    private suspend fun transformWeatherEntity(entity: WeatherEntity): WeatherInfo {
        val hourlyWeatherData = weatherDao.getHourlyWeatherDataByWeatherId(entity.id)
        return entity.toWeatherInfo(hourlyWeatherData)
    }

    companion object {
        private const val NO_CONNECTION_ERR =
            "No internet connection. Restore it and try again."
        private const val UNKNOWN_ERR = "Unknown NET REQUEST error!"
    }
}