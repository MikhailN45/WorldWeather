package com.naumov.worldweather.presentation.event

sealed interface Event {
    data object RefreshData : Event
    data class WeeklyForecastDayPressed(val day: Int) : Event
}