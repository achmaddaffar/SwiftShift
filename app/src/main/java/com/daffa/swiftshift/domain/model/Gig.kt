package com.daffa.swiftshift.domain.model

data class Gig(
    val title: String,
    val tag: String,
    val imageUrl: String?,
    val gigProviderName: String,
    val wage: Double,
    val location: String,
    val timestamp: Long
)