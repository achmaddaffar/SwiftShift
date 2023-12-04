package com.daffa.swiftshift.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.daffa.swiftshift.data.local.entity.GigEntity

@Dao
interface GigDao {

    @Upsert
    suspend fun upsertAll(gigs: List<GigEntity>)

    @Query("SELECT * FROM gigentity")
    fun pagingSource(): PagingSource<Int, GigEntity>

    @Query("DELETE FROM gigentity")
    suspend fun clearAll()
}