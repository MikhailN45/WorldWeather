package com.naumov.worldweather.presentation.ui.weather_overview

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
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
import com.naumov.worldweather.domain.model.weather.WeatherType
import com.naumov.worldweather.domain.model.weather.WeeklyForecast
import com.naumov.worldweather.presentation.ui.theme.WorldWeatherTheme
import com.naumov.worldweather.presentation.ui.theme.grayBackground


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PullToRefreshWeeklyForecastList(
    weeklyForecast: List<WeeklyForecast>,
    onDaySelected: (Int) -> Unit,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state = rememberPullToRefreshState()
    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        modifier = modifier,
        state = state
    ) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(weeklyForecast) { index, dayForecast ->
                WeeklyForecastItem(
                    forecast = dayForecast,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onDaySelected(index)
                        }
                )
            }
        }
    }
}

@Composable
fun WeeklyForecastItem(
    forecast: WeeklyForecast,
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
            containerColor = grayBackground
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = forecast.date,
                fontSize = 18.sp,
                color = Color.LightGray,
                modifier = Modifier.weight(3f)
            )
            Icon(
                imageVector = ImageVector.vectorResource(forecast.weatherType.iconRes),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(48.dp)
                    .weight(1f)
            )
            Column(
                modifier = modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.day),
                    fontSize = 12.sp,
                    color = Color.LightGray
                )
                Text(
                    text = stringResource(
                        R.string.degree,
                        forecast.dayTemperature
                    ),
                    fontSize = 16.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
            Column(
                modifier = modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.night),
                    fontSize = 12.sp,
                    color = Color.LightGray
                )
                Text(
                    text = stringResource(
                        R.string.degree,
                        forecast.nightTemperature
                    ),
                    fontSize = 16.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview
@Composable
fun WeatherByDayItemPreview() {
    WorldWeatherTheme {
        WeeklyForecastItem(weeklyItem)
    }
}

internal val weeklyItem = WeeklyForecast(
    date = "10 мая",
    dayTemperature = 15,
    nightTemperature = 5,
    weatherType = WeatherType.ClearSky
)