package com.naumov.worldweather.data.mappers

import com.naumov.worldweather.data.remote.WeatherDataDto
import com.naumov.worldweather.data.remote.WeatherDto
import com.naumov.worldweather.domain.weather.DayWeatherData
import com.naumov.worldweather.domain.weather.WeatherInfo
import com.naumov.worldweather.domain.weather.WeatherType
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
                pressure = (pressure*0.75).toInt(),
                windSpeed = windSpeed.toInt(),
                weatherType = WeatherType.fromWMO(weatherCode)
            )
        )
    }.groupBy {
        it.index / 24
    }.mapValues {
        it.value.map { it.data }
    }
}

fun WeatherDto.toWeatherInfo(): WeatherInfo {
    val weatherDataMap = weatherData.toWeatherDataMap()
    val now = LocalDateTime.now()
    val currentWeatherData = weatherDataMap[0]?.find {
        val hour = if(now.minute < 30) now.hour else now.hour + 1
        it.time.hour == hour
    }
    return WeatherInfo(
        weatherDataPerDay = weatherDataMap,
        currentDayWeatherData = currentWeatherData
    )
}