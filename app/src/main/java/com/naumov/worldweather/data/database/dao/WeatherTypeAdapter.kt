package com.naumov.worldweather.data.database.dao

import androidx.room.TypeConverter
import com.naumov.worldweather.domain.model.weather.WeatherType

class WeatherTypeAdapter {
    @TypeConverter
    fun fromWeatherType(weatherType: WeatherType): String {
        return weatherType.weatherDesc
    }

    @TypeConverter
    fun toWeatherType(desc: String): WeatherType {
        return when (desc) {
            "Ясное небо" -> WeatherType.ClearSky
            "Небольшая облачность" -> WeatherType.MainlyClear
            "Облачно с прояснениями" -> WeatherType.MainlyClear
            "Пасмурно" -> WeatherType.Overcast
            "Туман" -> WeatherType.Foggy
            "Туман с изморозью" -> WeatherType.DepositingRimeFog
            "Слабый моросящий дождь" -> WeatherType.LightDrizzle
            "Умеренный моросящий дождь" -> WeatherType.ModerateDrizzle
            "Сильный моросящий дождь" -> WeatherType.DenseDrizzle
            "Слабая ледяная морось" -> WeatherType.LightFreezingDrizzle
            "Сильная ледяная морось" -> WeatherType.DenseFreezingDrizzle
            "Небольшой дождь" -> WeatherType.SlightRain
            "Дождь" -> WeatherType.ModerateRain
            "Сильный дождь" -> WeatherType.HeavyRain
            "Сильный ледяной дождь" -> WeatherType.HeavyFreezingRain
            "Небольшой снегопад" -> WeatherType.SlightSnowFall
            "Умеренный снегопад" -> WeatherType.ModerateSnowFall
            "Сильный снегопад" -> WeatherType.HeavySnowFall
            "Снежные зерна" -> WeatherType.SnowGrains
            "Небольшой ливень" -> WeatherType.SlightRainShowers
            "Умеренный ливень" -> WeatherType.ModerateRainShowers
            "Сильный ливень" -> WeatherType.ViolentRainShowers
            "Легкий снегопад" -> WeatherType.SlightRainShowers
            "Умеренная гроза" -> WeatherType.ModerateThunderstorm
            "Гроза с небольшим градом" -> WeatherType.SlightHailThunderstorm
            "Гроза с сильным градом" -> WeatherType.HeavyHailThunderstorm
            else -> WeatherType.ClearSky
        }
    }
}