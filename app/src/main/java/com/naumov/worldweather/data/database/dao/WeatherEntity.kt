package com.naumov.worldweather.data.database.dao

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.naumov.worldweather.domain.model.weather.DayWeatherData
import java.time.LocalDateTime

@Entity(tableName = "weather_info")
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val weatherDataPerDay: String,
    val currentDayWeatherData: DayWeatherData?
)

@Entity
data class DayWeatherDataEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val time: LocalDateTime,
    val temperature: Int,
    val humidity: Int,
    val feelsTemperature: Int,
    val pressure: Int,
    val windSpeed: Int,
    val weatherType: String
)