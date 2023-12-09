package com.daffa.swiftshift.data.remote.response

data class GigProviderResponse(
    val fullName: String,
    val profileImageUrl: String,
    val joiningDate: Long,
    val email: String,
    val totalIncome: Double? = null,
    val gender: String? = null,
    val highestEducation: String? = null,
    val cvUrl: String? = null
)
