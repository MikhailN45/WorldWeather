package com.naumov.worldweather.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.naumov.worldweather.data.database.dao.HourlyWeatherDataEntity
import com.naumov.worldweather.data.database.dao.WeatherDao
import com.naumov.worldweather.data.database.dao.WeatherEntity
import com.naumov.worldweather.data.database.dao.WeatherTypeAdapter

@Database(
    entities = [WeatherEntity::class, HourlyWeatherDataEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    WeatherTypeAdapter::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao

    companion object {
        const val DATABASE = "room_database"
    }
}