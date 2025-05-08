package com.naumov.worldweather.domain.usecase

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import java.util.Locale
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

interface LocationNameProviderUseCase {
    suspend operator fun invoke(location: Location): String
}

class LocationNameProviderUseCaseImpl @Inject constructor(private val context: Context) :
    LocationNameProviderUseCase {

    override suspend operator fun invoke(location: Location): String = suspendCoroutine { cont ->
        val geoCoder = Geocoder(context, Locale.getDefault())
        location.let {
            if (Build.VERSION.SDK_INT >= 33) {
                geoCoder.getFromLocation(it.latitude, it.longitude, 1) { geoList ->
                    val currentCityName = geoList.firstOrNull()?.locality ?: ""
                    cont.resume(currentCityName)
                }
            } else {
                @Suppress("DEPRECATION") val geoList: List<Address>? =
                    geoCoder.getFromLocation(it.latitude, it.longitude, 1)
                val currentCityName = geoList?.firstOrNull()?.locality ?: ""
                cont.resume(currentCityName)
            }
        }
    }
}