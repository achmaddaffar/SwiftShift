package com.daffa.swiftshift.presentation.features.profile

import com.daffa.swiftshift.data.remote.response.GigWorkerResponse

data class ProfileGigWorkerState(
    val isLoading: Boolean = false,
    val gigWorker: GigWorkerResponse? = null
)
