package com.naumov.worldweather.presentation.ui.weather_main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import com.naumov.worldweather.domain.model.weather.DayWeatherData
import com.naumov.worldweather.domain.model.weather.WeatherInfo
import com.naumov.worldweather.domain.model.weather.WeatherType
import com.naumov.worldweather.domain.model.weather.WeeklyForecast
import com.naumov.worldweather.presentation.state.WeatherState
import com.naumov.worldweather.presentation.ui.theme.WorldWeatherTheme
import com.naumov.worldweather.presentation.ui.theme.blueBackground
import java.time.LocalDateTime

@Composable
fun TodayOverview(
    state: WeatherState,
    onDaySelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 15.dp,
                    shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                ),
            shape = RectangleShape,
            colors = CardDefaults.cardColors(
                containerColor = blueBackground
            )
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val todayWeather = state.weatherInfo?.currentDayWeatherData

                Text(
                    text = state.locationName,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White

                )
                Text(
                    text = stringResource(R.string.update_time, state.lastUpdateTime),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.LightGray
                )
                Row(
                    modifier = modifier.padding(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(
                            R.string.degree,
                            todayWeather?.temperature.toString()
                        ),
                        fontSize = 52.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Icon(
                        imageVector = ImageVector.vectorResource(
                            id = todayWeather?.weatherType?.iconRes ?: R.drawable.ic_sunny
                        ),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(56.dp)
                    )

                }
                Text(
                    text = todayWeather?.weatherType?.weatherDesc.toString(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.LightGray
                )
                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = modifier,
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_wind),
                            contentDescription = null,
                            tint = Color.Unspecified,
                            modifier = Modifier
                                .size(30.dp)
                                .padding(end = 8.dp)
                        )
                        Text(
                            text = stringResource(
                                id = R.string.meter_in_seconds,
                                todayWeather?.windSpeed.toString()
                            ),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Light,
                            color = Color.White
                        )
                    }
                    Row(
                        modifier = modifier,
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_pressure),
                            contentDescription = null,
                            tint = Color.Unspecified,
                            modifier = Modifier
                                .size(30.dp)
                                .padding(end = 8.dp)
                        )
                        Text(
                            text = stringResource(
                                R.string.millimeters_pressure,
                                todayWeather?.pressure.toString()
                            ),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Light,
                            color = Color.White,
                        )
                    }
                    Row(
                        modifier = modifier,
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_drop),
                            contentDescription = null,
                            tint = Color.Unspecified,
                            modifier = Modifier
                                .size(20.dp)
                                .padding(end = 8.dp)
                        )
                        Text(
                            text = stringResource(
                                R.string.percent,
                                todayWeather?.humidity.toString()
                            ),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Light,
                            color = Color.White
                        )
                    }
                }
                Text(
                    text = stringResource(
                        R.string.feels_temp,
                        todayWeather?.feelsTemperature.toString()
                    ),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.LightGray
                )
                Spacer(
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = stringResource(id = R.string.hourly_forecast),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                LazyRow(
                    modifier = modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.hourlyForecast) { dayWeatherData ->
                        WeatherByHourItem(
                            dayWeatherData
                        )
                    }

                }
            }
        }
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = stringResource(R.string.weekly_overcast_title),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Spacer(modifier = Modifier.size(8.dp))

        WeeklyForecastList(
            weeklyForecast = state.weeklyForecast,
            onDaySelected = { dayIndex ->
                onDaySelected(dayIndex)
            }
        )
    }
}

@Preview
@Composable
fun TodayOverviewPreview() {
    WorldWeatherTheme {
        TodayOverview(
            previewWeather,
            onDaySelected = { }
        )
    }
}

internal val previewWeather = WeatherState(
    weatherInfo = WeatherInfo(
        weatherDataPerDay = emptyMap(),
        currentDayWeatherData = DayWeatherData(
            time = LocalDateTime.now(),
            temperature = 8,
            humidity = 84,
            feelsTemperature = 1,
            pressure = 759,
            windSpeed = 0,
            weatherType = WeatherType.ClearSky
        )
    ),
    locationName = "Москва",
    lastUpdateTime = "16:03",
    hourlyForecast = listOf(
        DayWeatherData(
            time = LocalDateTime.now(),
            temperature = 15,
            humidity = 75,
            feelsTemperature = 11,
            pressure = 777,
            windSpeed = 7,
            weatherType = WeatherType.ClearSky
        ),
        DayWeatherData(
            time = LocalDateTime.now(),
            temperature = 15,
            humidity = 75,
            feelsTemperature = 11,
            pressure = 777,
            windSpeed = 7,
            weatherType = WeatherType.ClearSky
        ),
        DayWeatherData(
            time = LocalDateTime.now(),
            temperature = 15,
            humidity = 75,
            feelsTemperature = 11,
            pressure = 777,
            windSpeed = 7,
            weatherType = WeatherType.ClearSky
        ),
        DayWeatherData(
            time = LocalDateTime.now(),
            temperature = 15,
            humidity = 75,
            feelsTemperature = 11,
            pressure = 777,
            windSpeed = 7,
            weatherType = WeatherType.ClearSky
        ),
        DayWeatherData(
            time = LocalDateTime.now(),
            temperature = 15,
            humidity = 75,
            feelsTemperature = 11,
            pressure = 777,
            windSpeed = 7,
            weatherType = WeatherType.ClearSky
        ),
        DayWeatherData(
            time = LocalDateTime.now(),
            temperature = 15,
            humidity = 75,
            feelsTemperature = 11,
            pressure = 777,
            windSpeed = 7,
            weatherType = WeatherType.ClearSky
        ),
        DayWeatherData(
            time = LocalDateTime.now(),
            temperature = 15,
            humidity = 75,
            feelsTemperature = 11,
            pressure = 777,
            windSpeed = 7,
            weatherType = WeatherType.ClearSky
        ),
        DayWeatherData(
            time = LocalDateTime.now(),
            temperature = 15,
            humidity = 75,
            feelsTemperature = 11,
            pressure = 777,
            windSpeed = 7,
            weatherType = WeatherType.ClearSky
        )
    ),
    weeklyForecast = listOf(
        WeeklyForecast(
            date = "10 мая",
            dayTemperature = 15,
            nightTemperature = 5,
            weatherType = WeatherType.ClearSky
        ),
        WeeklyForecast(
            date = "11 мая",
            dayTemperature = 15,
            nightTemperature = 5,
            weatherType = WeatherType.ClearSky
        ),
        WeeklyForecast(
            date = "12 мая",
            dayTemperature = 15,
            nightTemperature = 5,
            weatherType = WeatherType.ClearSky
        ),
        WeeklyForecast(
            date = "13 мая",
            dayTemperature = 15,
            nightTemperature = 5,
            weatherType = WeatherType.ClearSky
        ),
        WeeklyForecast(
            date = "14 мая",
            dayTemperature = 15,
            nightTemperature = 5,
            weatherType = WeatherType.ClearSky
        ),
        WeeklyForecast(
            date = "15 мая",
            dayTemperature = 15,
            nightTemperature = 5,
            weatherType = WeatherType.ClearSky
        ),
        WeeklyForecast(
            date = "16 мая",
            dayTemperature = 15,
            nightTemperature = 5,
            weatherType = WeatherType.ClearSky
        )
    )
)