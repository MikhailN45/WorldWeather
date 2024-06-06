package com.naumov.worldweather.presentation.event

import com.naumov.worldweather.presentation.state.WeatherState

sealed interface Event {
    data object RefreshData : Event
    data class GetHourlyForecast(val state: WeatherState) : Event
    data class GetWeeklyForecast(val state: WeatherState) : Event
}