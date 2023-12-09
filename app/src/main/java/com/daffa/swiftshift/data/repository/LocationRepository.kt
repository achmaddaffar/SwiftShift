package com.daffa.swiftshift.data.repository

import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.os.Build
import android.os.Looper
import androidx.annotation.RequiresApi
import com.daffa.swiftshift.domain.repository.ILocationRepository
import com.daffa.swiftshift.util.hasLocationPermission
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val context: Context,
    private val locationClient: FusedLocationProviderClient,
) : ILocationRepository {

    @RequiresApi(Build.VERSION_CODES.S)
    @SuppressLint("MissingPermission")
    override fun getCurrentLocation(): Flow<LatLng?> = callbackFlow {
        if (!context.hasLocationPermission()) {
            trySend(null)
            return@callbackFlow
        }

        val request = LocationRequest.Builder(300000L)
            .setIntervalMillis(300000L)
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .build()

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                locationResult.locations.lastOrNull()?.let {
                    trySend(LatLng(it.latitude, it.longitude))
                }
            }
        }

        locationClient.requestLocationUpdates(
            request,
            locationCallback,
            Looper.getMainLooper()
        )

        awaitClose {
            locationClient.removeLocationUpdates(locationCallback)
        }
    }

    override fun getAddressFromLocation(latLng: LatLng): Flow<Address> = flow { }
}