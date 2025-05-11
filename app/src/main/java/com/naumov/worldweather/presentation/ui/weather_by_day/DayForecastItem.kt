package com.naumov.worldweather.presentation.ui.weather_by_day

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.naumov.worldweather.R
import com.naumov.worldweather.domain.model.util.WeatherClass
import com.naumov.worldweather.domain.model.weather.DetailedDayForecast
import com.naumov.worldweather.presentation.ui.theme.WorldWeatherTheme
import com.naumov.worldweather.presentation.ui.theme.blueBackground


@Composable
fun DayForecastItem(
    forecast: DetailedDayForecast?,
    type: WeatherClass,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
            .shadow(
                elevation = 15.dp,
                shape = RoundedCornerShape(16.dp)
            ),
        shape = RectangleShape,
        colors = CardDefaults.cardColors(
            containerColor = blueBackground
        )
    ) {
        var textMorning = ""
        var textDay = ""
        var textEvening = ""
        var textNight = ""
        @DrawableRes var icon = 0
        var title = ""

        when (type) {
            WeatherClass.WIND -> {
                textMorning = forecast?.windMorning.toString()
                textDay = forecast?.windDay.toString()
                textEvening = forecast?.windEvening.toString()
                textNight = forecast?.windNight.toString()
                icon = R.drawable.ic_wind
                title = stringResource(R.string.wind_title)
            }
            WeatherClass.HUMIDITY -> {
                textMorning = forecast?.humidityMorning.toString()
                textDay = forecast?.humidityDay.toString()
                textEvening = forecast?.humidityEvening.toString()
                textNight = forecast?.humidityNight.toString()
                icon = R.drawable.ic_drop
                title = stringResource(R.string.humidity_daily)
            }

            WeatherClass.PRESSURE -> {
                textMorning = forecast?.pressureMorning.toString()
                textDay = forecast?.pressureDay.toString()
                textEvening = forecast?.pressureEvening.toString()
                textNight = forecast?.pressureNight.toString()
                icon = R.drawable.ic_pressure
                title = stringResource(R.string.pressure_daily)
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = modifier
                    .fillMaxWidth()
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(icon),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .size(24.dp)
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.morning),
                    fontWeight = FontWeight.Light,
                    fontSize = 14.sp,
                    color = Color.LightGray
                )
                Text(
                    text = stringResource(R.string.day),
                    fontWeight = FontWeight.Light,
                    fontSize = 14.sp,
                    color = Color.LightGray
                )
                Text(
                    text = stringResource(R.string.evening),
                    fontWeight = FontWeight.Light,
                    fontSize = 14.sp,
                    color = Color.LightGray
                )
                Text(
                    text = stringResource(R.string.night),
                    fontWeight = FontWeight.Light,
                    fontSize = 14.sp,
                    color = Color.LightGray
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = textMorning,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White,
                )
                Text(
                    text = textDay,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White,
                )
                Text(
                    text = textEvening,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White,
                    )
                Text(
                    text = textNight,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White,
                )
            }
        }
    }
}

@Preview
@Composable
fun DayForecastItemPreview() {
    WorldWeatherTheme {
        DayForecastItem(
            dayForecast,
            type = WeatherClass.PRESSURE
        )
    }
}
