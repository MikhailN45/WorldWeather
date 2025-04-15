package com.naumov.worldweather.domain.preferences

interface PreferencesManager {
    fun saveLastUpdateTime(epochMillis: Long)
    fun getLastUpdateTime(): Long?
}