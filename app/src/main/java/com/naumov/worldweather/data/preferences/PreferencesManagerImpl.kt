package com.naumov.worldweather.data.preferences

import android.content.Context
import com.naumov.worldweather.domain.preferences.PreferencesManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import androidx.core.content.edit

@Singleton
class PreferencesManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : PreferencesManager { //LDS
    private val sharedPreferences = context.getSharedPreferences("weather_prefs", Context.MODE_PRIVATE)
    private val lastUpdateKey = "last_update_time"

    override fun saveLastUpdateTime(epochMillis: Long) {
        sharedPreferences.edit {
            putLong(lastUpdateKey, epochMillis)
        }
    }

    override fun getLastUpdateTime(): Long? {
        return if (sharedPreferences.contains(lastUpdateKey)) {
            sharedPreferences.getLong(lastUpdateKey, 0L)
        } else {
            null
        }
    }
}