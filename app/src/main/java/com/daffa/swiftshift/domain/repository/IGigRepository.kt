package com.daffa.swiftshift.domain.repository

import android.net.Uri
import androidx.paging.PagingData
import com.daffa.swiftshift.data.local.entity.GigEntity
import com.daffa.swiftshift.data.remote.request.CreateGigRequest
import com.daffa.swiftshift.data.remote.response.GigDetailResponse
import com.daffa.swiftshift.domain.model.Gig
import com.daffa.swiftshift.util.Resource
import com.daffa.swiftshift.util.SimpleResource
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow

interface IGigRepository {

    suspend fun createGig(
        request: CreateGigRequest,
        imageUri: Uri?
    ): Flow<SimpleResource>

    fun getNearbyGigs(
        latitude: Double,
        longitude: Double,
    ): Flow<PagingData<GigEntity>>

    suspend fun getNearbyGigsPreview(
        latLng: LatLng,
    ): Flow<Resource<List<Gig>>>

    suspend fun getRecommendedGigs(
        latLng: LatLng,
    ): Flow<Resource<List<Gig>>>

    suspend fun searchGig(
        query: String
    ): Flow<Resource<List<Gig>>>

    suspend fun getGigDetail(
        gigId: String
    ): Flow<Resource<GigDetailResponse>>
}