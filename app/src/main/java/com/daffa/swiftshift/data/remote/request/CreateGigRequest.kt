package com.daffa.swiftshift.data.remote.request

data class CreateGigRequest(
    val title: String,
    val description: String,
    val tag: String,
    val gigProviderId: String,
    val maxApplier: Int,
    val deadlineDate: Long,
    val salary: Double,
    val latitude: Double,
    val longitude: Double,
)
