package com.naumov.worldweather.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeatherDao {
    @Query("SELECT * FROM weather_info LIMIT 1")
    suspend fun getLastWeather(): WeatherEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: WeatherEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHourlyWeatherData(hourlyWeatherData: List<HourlyWeatherDataEntity>)
}