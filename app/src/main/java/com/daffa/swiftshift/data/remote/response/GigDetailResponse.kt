package com.daffa.swiftshift.data.remote.response

data class GigDetailResponse(
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
    val latitude: Double,
    val longitude: Double,
    val timestamp: Long,
    val id: String
)
