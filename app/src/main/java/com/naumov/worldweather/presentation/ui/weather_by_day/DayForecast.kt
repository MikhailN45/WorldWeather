package com.naumov.worldweather.presentation.ui.weather_by_day

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.naumov.worldweather.R
import com.naumov.worldweather.domain.model.util.WeatherClass
import com.naumov.worldweather.presentation.state.WeatherState
import com.naumov.worldweather.presentation.ui.theme.WorldWeatherTheme


@Composable
fun DayForecast(
    state: WeatherState,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val forecast = state.detailedDayForecast
    val scrollState = rememberScrollState()
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(Color.Black)
    ) {
        IconButton(onClick = onBack) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }
        Text(
            text = stringResource(
                R.string.forecast_date,
                forecast?.date ?: ""
            ),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
        Spacer(
            modifier = Modifier
                .size(16.dp)
        )

        DayTemperatureDetails(
            forecast = forecast,
            modifier = modifier
        )
        Spacer(
            modifier = Modifier
                .size(16.dp)
        )

        DayForecastItem(
            forecast = forecast,
            type = WeatherClass.WIND,
            modifier = modifier
        )
        Spacer(
            modifier = Modifier
                .size(16.dp)
        )

        DayForecastItem(
            forecast = forecast,
            type = WeatherClass.HUMIDITY,
            modifier = modifier
        )
        Spacer(
            modifier = Modifier
                .size(16.dp)
        )

        DayForecastItem(
            forecast = forecast,
            type = WeatherClass.PRESSURE,
            modifier = modifier
        )
    }
}

@Preview
@Composable
fun DayForecastPreview() {
    WorldWeatherTheme {
        DayForecast(
            WeatherState(
                detailedDayForecast = dayForecast
            ),
            onBack = {}
        )
    }
}
