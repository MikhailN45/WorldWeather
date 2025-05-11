package com.naumov.worldweather.presentation.ui.weather_by_day

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.naumov.worldweather.domain.model.weather.DetailedDayForecast
import com.naumov.worldweather.domain.model.weather.WeatherType
import com.naumov.worldweather.presentation.ui.theme.WorldWeatherTheme
import com.naumov.worldweather.presentation.ui.theme.blueBackground

@Composable
fun DayTemperatureDetails(
    forecast: DetailedDayForecast?,
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
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = stringResource(R.string.temperature_daily),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.White
            )
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = modifier.fillMaxWidth()
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
                modifier = modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(
                            forecast?.weatherTypeMorning?.iconRes ?: R.drawable.ic_sunny
                        ),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .size(50.dp)
                )
                Icon(
                    imageVector = ImageVector.vectorResource(
                        forecast?.weatherTypeDay?.iconRes ?: R.drawable.ic_sunny
                    ),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .size(50.dp)
                )
                Icon(
                    imageVector = ImageVector.vectorResource(
                        forecast?.weatherTypeEvening?.iconRes ?: R.drawable.ic_sunny
                    ),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .size(50.dp)
                )
                Icon(
                    imageVector = ImageVector.vectorResource(
                        forecast?.weatherTypeNight?.iconRes ?: R.drawable.ic_sunny
                    ),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .size(50.dp)
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(
                        R.string.degree,
                        forecast?.temperatureMorning.toString()
                    ),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.padding(start = 12.dp)
                )
                Text(
                    text = stringResource(
                        R.string.degree,
                        forecast?.temperatureDay.toString()
                    ),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.padding(start = 12.dp)
                )
                Text(
                    text = stringResource(
                        R.string.degree,
                        forecast?.temperatureEvening.toString()
                    ),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.padding(start = 12.dp)
                )
                Text(
                    text = stringResource(
                        R.string.degree,
                        forecast?.temperatureNight.toString()
                    ),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.padding(start = 12.dp)
                )
            }
            Text(
                text = stringResource(R.string.apparent_as),
                fontWeight = FontWeight.Light,
                fontSize = 14.sp,
                color = Color.LightGray
            )
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(
                        R.string.degree,
                        forecast?.feelTemperatureMorning.toString()
                    ),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.White,
                    modifier = Modifier.padding(start = 8.dp)
                )
                Text(
                    text = stringResource(
                        R.string.degree,
                        forecast?.feelTemperatureDay.toString()
                    ),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.White,
                    modifier = Modifier.padding(start = 8.dp)
                )
                Text(
                    text = stringResource(
                        R.string.degree,
                        forecast?.feelTemperatureEvening.toString()
                    ),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.White,
                    modifier = Modifier.padding(start = 8.dp)
                )
                Text(
                    text = stringResource(
                        R.string.degree,
                        forecast?.temperatureNight.toString()
                    ),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.White,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun DayTemperatureDetailsPreview(){
    WorldWeatherTheme {
        DayTemperatureDetails(forecast = dayForecast)
    }
}

internal val dayForecast = DetailedDayForecast(
    date = "10 мая",
    weatherTypeMorning = WeatherType.ClearSky,
    weatherTypeDay = WeatherType.ClearSky,
    weatherTypeEvening = WeatherType.ClearSky,
    weatherTypeNight = WeatherType.ClearSky,
    temperatureMorning = 5,
    temperatureDay = 10,
    temperatureEvening = 15,
    temperatureNight = 20,
    feelTemperatureMorning = 5,
    feelTemperatureDay = 10,
    feelTemperatureEvening = 15,
    feelTemperatureNight = 20,
    windMorning = 1,
    windDay = 2,
    windEvening = 3,
    windNight = 4,
    humidityMorning = 50,
    humidityDay = 55,
    humidityEvening = 60,
    humidityNight = 65,
    pressureMorning = 555,
    pressureDay = 666,
    pressureEvening = 777,
    pressureNight = 888
)
