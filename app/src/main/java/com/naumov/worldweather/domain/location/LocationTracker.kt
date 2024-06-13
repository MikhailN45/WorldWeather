package com.naumov.worldweather.domain.location

import android.location.Location

fun interface LocationTracker {
    suspend fun getCurrentLocation(): Location?
}