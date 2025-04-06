package com.naumov.worldweather.data.remote

import com.google.gson.annotations.SerializedName

/**
@JsonClass(generateAdapter = true)
data class WeatherResponse(
    @field:Json(name = "hourly")
    val weatherData: WeatherDataDto
)*/

data class WeatherResponse(
    @SerializedName("hourly")
    val weatherData: WeatherDataDto
)
