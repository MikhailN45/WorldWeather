package com.naumov.worldweather.di

import android.content.Context
import androidx.room.Room
import com.naumov.worldweather.data.database.AppDatabase
import com.naumov.worldweather.data.database.dao.WeatherDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(applicationContext: Context): AppDatabase {
        return Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            AppDatabase.DATABASE
        ).build()
    }

    @Provides
    fun provideWeatherDao(database: AppDatabase): WeatherDao {
        return database.weatherDao()
    }
}