package com.naumov.worldweather.data.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherResponse(
    @field:Json(name = "hourly")
    val weatherData: WeatherDataDto
)