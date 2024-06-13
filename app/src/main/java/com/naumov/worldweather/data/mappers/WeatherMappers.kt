package com.naumov.worldweather.data.mappers

import com.naumov.worldweather.data.remote.WeatherDataDto
import com.naumov.worldweather.data.remote.WeatherResponse
import com.naumov.worldweather.domain.model.weather.DayWeatherData
import com.naumov.worldweather.domain.model.weather.WeatherInfo
import com.naumov.worldweather.domain.model.weather.WeatherType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private data class IndexedWeatherData(
    val index: Int,
    val data: DayWeatherData
)

fun WeatherDataDto.toWeatherDataMap(): Map<Int, List<DayWeatherData>> {
    return time.mapIndexed { index, time ->
        val temperature = temperatures[index]
        val humidity = humidities[index]
        val apparentTemperature = apparentTemperatures[index]
        val pressure = pressures[index]
        val weatherCode = weatherCodes[index]
        val windSpeed = windSpeeds[index]
        IndexedWeatherData(
            index = index,
            data = DayWeatherData(
                time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
                temperature = temperature.toInt(),
                humidity = humidity.toInt(),
                feelsTemperature = apparentTemperature.toInt(),
                pressure = (pressure * 0.75).toInt(),
                windSpeed = windSpeed.toInt(),
                weatherType = WeatherType.fromWMO(weatherCode)
            )
        )
    }.groupBy {
        it.index / 24
    }.mapValues { listEntry ->
        listEntry.value.map { it.data }
    }
}

fun WeatherResponse.toWeatherInfo(): WeatherInfo {
    val weatherDataMap = weatherData.toWeatherDataMap()
    val now = LocalDateTime.now()
    val currentWeatherData = weatherDataMap[0]?.find {
        it.time.hour ==
                if (now.minute < 30) now.hour
                else if (now.hour != 23) now.hour + 1
                else 0
    }
    return WeatherInfo(
        weatherDataPerDay = weatherDataMap,
        currentDayWeatherData = currentWeatherData
    )
}