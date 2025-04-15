package com.naumov.worldweather.data.database.dao

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "weather_info")
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val startTimeEpoch: Long
)

@Entity(
    tableName = "hourly_weather_data",
    foreignKeys = [ForeignKey(
        entity = WeatherEntity::class,
        parentColumns = ["id"],
        childColumns = ["weatherInfoId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["weatherInfoId"])]
)
data class HourlyWeatherDataEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val weatherInfoId: Int,
    val hour: Int,
    val rawTime: String,
    val temperature: Int,
    val humidity: Int,
    val feelsTemperature: Int,
    val pressure: Int,
    val windSpeed: Int,
    val weatherWmoCode: Int
)