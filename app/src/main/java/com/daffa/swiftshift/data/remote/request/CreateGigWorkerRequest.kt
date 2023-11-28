package com.daffa.swiftshift.data.remote.request

data class CreateGigWorkerRequest(
    val fullName: String,
    val email: String,
    val password: String
)