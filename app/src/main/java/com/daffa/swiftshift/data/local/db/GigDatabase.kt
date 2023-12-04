package com.daffa.swiftshift.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.daffa.swiftshift.data.local.dao.GigDao
import com.daffa.swiftshift.data.local.entity.GigEntity

@Database(
    entities = [GigEntity::class],
    version = 1
)
abstract class GigDatabase: RoomDatabase() {

    abstract val dao: GigDao
}