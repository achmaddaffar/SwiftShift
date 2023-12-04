package com.daffa.swiftshift.domain.repository

import android.location.Address
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow

interface ILocationRepository {

    fun getCurrentLocation(): Flow<LatLng?>

    fun getAddressFromLocation(latLng: LatLng): Flow<Address>
}