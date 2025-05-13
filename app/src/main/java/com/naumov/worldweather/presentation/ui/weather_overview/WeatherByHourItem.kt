package com.naumov.worldweather.presentation.ui.weather_overview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.naumov.worldweather.R
import com.naumov.worldweather.domain.model.weather.DayWeatherData
import com.naumov.worldweather.domain.model.weather.WeatherType
import com.naumov.worldweather.presentation.ui.theme.WorldWeatherTheme
import com.naumov.worldweather.presentation.ui.theme.iconOrange
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Composable
fun WeatherByHourItem(
    dayWeatherData: DayWeatherData,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(8.dp),
    ) {
        Text(
            text = dayWeatherData.time.format(DateTimeFormatter.ofPattern("HH:mm")),
            fontSize = 14.sp,
            fontWeight = FontWeight.Light,
            color = iconOrange
        )
        Icon(
            imageVector = ImageVector.vectorResource(id = dayWeatherData.weatherType.iconRes),
            contentDescription = dayWeatherData.weatherType.weatherDesc,
            tint = Color.Unspecified,
            modifier = Modifier
                .size(40.dp)
        )
        Text(
            text = stringResource(R.string.degree, dayWeatherData.temperature),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Preview
@Composable
fun WeatherByHourItemPreview() {
    WorldWeatherTheme {
        WeatherByHourItem(
            previewByHours
        )
    }
}

internal val previewByHours = DayWeatherData(
    time = LocalDateTime.now(),
    temperature = 16,
    humidity = 77,
    feelsTemperature = 11,
    pressure = 777,
    windSpeed = 7,
    weatherType = WeatherType.ClearSky
)
