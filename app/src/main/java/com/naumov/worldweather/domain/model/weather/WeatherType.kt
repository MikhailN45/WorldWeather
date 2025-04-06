package com.naumov.worldweather.domain.model.weather

import androidx.annotation.DrawableRes
import com.naumov.worldweather.R

sealed class WeatherType(
    val weatherDesc: String,
    @DrawableRes val iconRes: Int
) {
    data object ClearSky : WeatherType(
        weatherDesc = "Ясное небо",
        iconRes = R.drawable.ic_sunny
    )

    data object MainlyClear : WeatherType(
        weatherDesc = "Небольшая облачность",
        iconRes = R.drawable.ic_sunnycloudy
    )

    data object PartlyCloudy : WeatherType(
        weatherDesc = "Облачно с прояснениями",
        iconRes = R.drawable.ic_cloudy
    )

    data object Overcast : WeatherType(
        weatherDesc = "Пасмурно",
        iconRes = R.drawable.ic_very_cloudy
    )

    data object Foggy : WeatherType(
        weatherDesc = "Туман",
        iconRes = R.drawable.ic_very_cloudy
    )

    data object DepositingRimeFog : WeatherType(
        weatherDesc = "Туман с изморозью",
        iconRes = R.drawable.ic_very_cloudy
    )

    data object LightDrizzle : WeatherType(
        weatherDesc = "Слабый моросящий дождь",
        iconRes = R.drawable.ic_rainshower
    )

    data object ModerateDrizzle : WeatherType(
        weatherDesc = "Умеренный моросящий дождь",
        iconRes = R.drawable.ic_rainshower
    )

    data object DenseDrizzle : WeatherType(
        weatherDesc = "Сильный моросящий дождь",
        iconRes = R.drawable.ic_rainshower
    )

    data object LightFreezingDrizzle : WeatherType(
        weatherDesc = "Слабая ледяная морось",
        iconRes = R.drawable.ic_snowyrainy
    )

    data object DenseFreezingDrizzle : WeatherType(
        weatherDesc = "Сильная ледяная морось",
        iconRes = R.drawable.ic_snowyrainy
    )

    data object SlightRain : WeatherType(
        weatherDesc = "Небольшой дождь",
        iconRes = R.drawable.ic_rainy
    )

    data object ModerateRain : WeatherType(
        weatherDesc = "Дождь",
        iconRes = R.drawable.ic_rainy
    )

    data object HeavyRain : WeatherType(
        weatherDesc = "Сильный дождь",
        iconRes = R.drawable.ic_rainy
    )

    data object HeavyFreezingRain : WeatherType(
        weatherDesc = "Сильный ледяной дождь",
        iconRes = R.drawable.ic_snowyrainy
    )

    data object SlightSnowFall : WeatherType(
        weatherDesc = "Небольшой снегопад",
        iconRes = R.drawable.ic_snowy
    )

    data object ModerateSnowFall : WeatherType(
        weatherDesc = "Умеренный снегопад",
        iconRes = R.drawable.ic_heavysnow
    )

    data object HeavySnowFall : WeatherType(
        weatherDesc = "Сильный снегопад",
        iconRes = R.drawable.ic_heavysnow
    )

    data object SnowGrains : WeatherType(
        weatherDesc = "Снежные зерна",
        iconRes = R.drawable.ic_heavysnow
    )

    data object SlightRainShowers : WeatherType(
        weatherDesc = "Небольшой ливень",
        iconRes = R.drawable.ic_rainshower
    )

    data object ModerateRainShowers : WeatherType(
        weatherDesc = "Умеренный ливень",
        iconRes = R.drawable.ic_rainshower
    )

    data object ViolentRainShowers : WeatherType(
        weatherDesc = "Сильный ливень",
        iconRes = R.drawable.ic_rainshower
    )

    data object SlightSnowShowers : WeatherType(
        weatherDesc = "Легкий снегопад",
        iconRes = R.drawable.ic_snowy
    )

    data object HeavySnowShowers : WeatherType(
        weatherDesc = "Сильный снегопад",
        iconRes = R.drawable.ic_snowy
    )

    data object ModerateThunderstorm : WeatherType(
        weatherDesc = "Умеренная гроза",
        iconRes = R.drawable.ic_thunder
    )

    data object SlightHailThunderstorm : WeatherType(
        weatherDesc = "Гроза с небольшим градом",
        iconRes = R.drawable.ic_rainythunder
    )

    data object HeavyHailThunderstorm : WeatherType(
        weatherDesc = "Гроза с сильным градом",
        iconRes = R.drawable.ic_rainythunder
    )

    companion object {
        fun fromWMO(code: Int): WeatherType {
            return when (code) {
                0 -> ClearSky
                1 -> MainlyClear
                2 -> PartlyCloudy
                3 -> Overcast
                45 -> Foggy
                48 -> DepositingRimeFog
                51 -> LightDrizzle
                53 -> ModerateDrizzle
                55 -> DenseDrizzle
                56 -> LightFreezingDrizzle
                57 -> DenseFreezingDrizzle
                61 -> SlightRain
                63 -> ModerateRain
                65 -> HeavyRain
                66 -> LightFreezingDrizzle
                67 -> HeavyFreezingRain
                71 -> SlightSnowFall
                73 -> ModerateSnowFall
                75 -> HeavySnowFall
                77 -> SnowGrains
                80 -> SlightRainShowers
                81 -> ModerateRainShowers
                82 -> ViolentRainShowers
                85 -> SlightSnowShowers
                86 -> HeavySnowShowers
                95 -> ModerateThunderstorm
                96 -> SlightHailThunderstorm
                99 -> HeavyHailThunderstorm
                else -> ClearSky
            }
        }

        fun toWMO(type: WeatherType): Int {
            return when (type) {
                ClearSky -> 0
                MainlyClear -> 1
                PartlyCloudy -> 2
                Overcast -> 3
                Foggy -> 45
                DepositingRimeFog -> 48
                LightDrizzle -> 51
                ModerateDrizzle -> 53
                DenseDrizzle -> 55
                LightFreezingDrizzle -> 56
                DenseFreezingDrizzle -> 57
                SlightRain -> 61
                ModerateRain -> 63
                HeavyRain -> 65
                HeavyFreezingRain -> 67
                SlightSnowFall -> 71
                ModerateSnowFall -> 73
                HeavySnowFall -> 75
                SnowGrains -> 77
                SlightRainShowers -> 80
                ModerateRainShowers -> 81
                ViolentRainShowers -> 82
                SlightSnowShowers -> 85
                HeavySnowShowers -> 86
                ModerateThunderstorm -> 95
                SlightHailThunderstorm -> 96
                HeavyHailThunderstorm -> 99
            }
        }
    }
}