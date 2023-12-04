package com.daffa.swiftshift.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GigEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val imageUrl: String,
    val description: String,
    val tag: String,
    val gigProviderId: String,
    val gigProviderName: String,
    val maxApplier: Int,
    val currentApplier: Int,
    val deadlineDate: Long,
    val salary: Double,
    val longitude: Double,
    val latitude: Double,
    val timestamp: Long,
    val distance: Double,
)