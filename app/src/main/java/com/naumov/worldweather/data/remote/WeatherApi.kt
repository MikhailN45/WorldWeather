package com.naumov.worldweather.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET(WEEKLY_OVERCAST_ROUTE)
    suspend fun getWeatherData(
        @Query("latitude") lat: Double,
        @Query("longitude") long: Double
    ): WeatherDto

    companion object{
        private const val WEEKLY_OVERCAST_ROUTE =
            "v1/forecast?hourly=temperature_2m,relative_humidity_2m,apparent_temperature," +
                    "pressure_msl,weather_code,wind_speed_10m&wind_speed_unit=ms&timezone=auto"
    }
}