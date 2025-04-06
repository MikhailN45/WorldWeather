package com.naumov.worldweather.data.remote

import com.google.gson.annotations.SerializedName

/**@JsonClass(generateAdapter = true)
data class WeatherDataDto(
    val time: List<String>,
    @field:Json(name = "temperature_2m")
    val temperatures: List<Double>,
    @field:Json(name = "relative_humidity_2m")
    val humidities: List<Double>,
    @field:Json(name = "apparent_temperature")
    val apparentTemperatures: List<Double>,
    @field:Json(name = "pressure_msl")
    val pressures: List<Double>,
    @field:Json(name = "weather_code")
    val weatherCodes: List<Int>,
    @field:Json(name = "wind_speed_10m")
    val windSpeeds: List<Double>
)*/

data class WeatherDataDto(
    val time: List<String>,
    @SerializedName("temperature_2m")
    val temperatures: List<Double>,
    @SerializedName("relative_humidity_2m")
    val humidities: List<Double>,
    @SerializedName("apparent_temperature")
    val apparentTemperatures: List<Double>,
    @SerializedName("pressure_msl")
    val pressures: List<Double>,
    @SerializedName("weather_code")
    val weatherCodes: List<Int>,
    @SerializedName("wind_speed_10m")
    val windSpeeds: List<Double>
)
