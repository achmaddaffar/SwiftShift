package com.daffa.swiftshift.data.repository

import android.content.SharedPreferences
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.daffa.swiftshift.data.local.db.GigDatabase
import com.daffa.swiftshift.data.local.entity.GigEntity
import com.daffa.swiftshift.data.local.mapper.toGig
import com.daffa.swiftshift.data.remote.api.GigApi
import com.daffa.swiftshift.data.remote.request.CreateGigRequest
import com.daffa.swiftshift.data.remote_mediator.GigRemoteMediator
import com.daffa.swiftshift.domain.model.Gig
import com.daffa.swiftshift.domain.repository.IGigRepository
import com.daffa.swiftshift.util.Constants
import com.daffa.swiftshift.util.Constants.Empty
import com.daffa.swiftshift.util.Resource
import com.daffa.swiftshift.util.SimpleResource
import com.daffa.swiftshift.util.UiText
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException

@OptIn(ExperimentalPagingApi::class)
class GigRepository(
    private val gigApi: GigApi,
    private val gigDb: GigDatabase,
    private val sharedPreferences: SharedPreferences,
) : IGigRepository {
    override suspend fun createGig(request: CreateGigRequest): Flow<SimpleResource> =
        flow<SimpleResource> {

        }.flowOn(Dispatchers.IO)

    override fun getNearbyGigs(
        latitude: Double,
        longitude: Double,
    ): Flow<PagingData<GigEntity>> {
        val token = sharedPreferences.getString(Constants.KEY_JWT_TOKEN, String.Empty).toString()

        return Pager(
            config = PagingConfig(pageSize = Constants.DEFAULT_NEARBY_GIGS_PAGE_SIZE),
            remoteMediator = GigRemoteMediator(
                gigDb = gigDb,
                gigApi = gigApi,
                token = "Bearer $token",
                latitude = latitude,
                longitude = longitude
            ),
            pagingSourceFactory = {
                gigDb.dao.pagingSource()
            }
        )
            .flow
            .flowOn(Dispatchers.IO)
    }

    override suspend fun getNearbyGigsPreview(latLng: LatLng): Flow<Resource<List<Gig>>> = flow {
        emit(Resource.Loading())
        try {
            val token =
                sharedPreferences.getString(Constants.KEY_JWT_TOKEN, String.Empty).toString()

            val response = gigApi.getNearbyGigs(
                token = "Bearer $token",
                latitude = latLng.latitude,
                longitude = latLng.longitude,
                page = 0,
                pageSize = 10
            )
            if (response.successful) {
                val gigs = response.data!!.map { it.toGig() }
                emit(Resource.Success(gigs))
            } else {
                response.message?.let { msg ->
                    emit(Resource.Error(UiText.DynamicString(msg)))
                } ?: emit(Resource.Error(UiText.unknownError()))
            }
        } catch (e: HttpException) {
            emit(Resource.Error(UiText.DynamicString(e.toString())))
        } catch (e: Exception) {
            emit(Resource.Error(UiText.DynamicString(e.toString())))
        }
    }.flowOn(Dispatchers.IO)


    override suspend fun getRecommendedGigs(
        latLng: LatLng,
    ): Flow<Resource<List<Gig>>> = flow {
        emit(Resource.Loading())
        try {
            val token =
                sharedPreferences.getString(Constants.KEY_JWT_TOKEN, String.Empty).toString()

            val response = gigApi.getRecommendedGigs(
                token = "Bearer $token",
                latitude = latLng.latitude,
                longitude = latLng.longitude
            )
            if (response.successful) {
                val gigs = response.data!!.map { it.toGig() }
                emit(Resource.Success(gigs))
            } else {
                response.message?.let { msg ->
                    emit(Resource.Error(UiText.DynamicString(msg)))
                } ?: emit(Resource.Error(UiText.unknownError()))
            }
        } catch (e: HttpException) {
            emit(Resource.Error(UiText.DynamicString(e.toString())))
        } catch (e: Exception) {
            emit(Resource.Error(UiText.DynamicString(e.toString())))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun searchGig(
        query: String
    ): Flow<Resource<List<Gig>>> = flow {
        emit(Resource.Loading())

        try {
            val token =
                sharedPreferences.getString(Constants.KEY_JWT_TOKEN, String.Empty).toString()

            val response = gigApi.searchGig(
                token = "Bearer $token",
                query = query
            )

            if (response.successful) {
                val gigs = response.data!!.map { it.toGig() }
                emit(Resource.Success(gigs))
            } else {
                response.message?.let { msg ->
                    emit(Resource.Error(UiText.DynamicString(msg)))
                } ?: emit(Resource.Error(UiText.unknownError()))
            }
        } catch (e: HttpException) {
            emit(Resource.Error(UiText.DynamicString(e.toString())))
        } catch (e: Exception) {
            emit(Resource.Error(UiText.DynamicString(e.toString())))
        }
    }.flowOn(Dispatchers.IO)
}