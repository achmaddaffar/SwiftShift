package com.daffa.swiftshift.data.remote_mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.daffa.swiftshift.data.local.db.GigDatabase
import com.daffa.swiftshift.data.local.entity.GigEntity
import com.daffa.swiftshift.data.local.mapper.toGigEntity
import com.daffa.swiftshift.data.remote.api.GigApi
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class GigRemoteMediator(
    private val gigDb: GigDatabase,
    private val gigApi: GigApi,
    private val latitude: Double,
    private val token: String,
    private val longitude: Double
) : RemoteMediator<Int, GigEntity>() {

    private var currentPage = 0

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, GigEntity>,
    ): MediatorResult {
        return try {
            val loadKey = when(loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        1
                    } else {
                        ++currentPage
                    }
                }
            }

            val response = gigApi.getNearbyGigs(
                token = token,
                latitude = latitude,
                longitude = longitude,
                page = loadKey,
                pageSize = state.config.pageSize
            )
            val gigs = response.data!!

            println("gigs REMOTE MEDIATOR ${gigs.size}")
            println("gigs REMOTE MEDIATOR isSuccess ${response.successful}")

            gigDb.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    gigDb.dao.clearAll()
                }
                val gigEntities = gigs.map { it.toGigEntity() }
                gigDb.dao.upsertAll(gigEntities)
            }

            MediatorResult.Success(
                endOfPaginationReached = gigs.isEmpty()
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}