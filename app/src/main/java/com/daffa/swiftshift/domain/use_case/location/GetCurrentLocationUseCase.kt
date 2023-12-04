package com.daffa.swiftshift.domain.use_case.location

import android.os.Build
import androidx.annotation.RequiresApi
import com.daffa.swiftshift.domain.repository.ILocationRepository
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentLocationUseCase @Inject constructor(
    private val locationRepository: ILocationRepository
) {

    @RequiresApi(Build.VERSION_CODES.S)
    operator fun invoke(): Flow<LatLng?> = locationRepository.getCurrentLocation()
}