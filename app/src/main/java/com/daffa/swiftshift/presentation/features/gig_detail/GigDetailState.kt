package com.daffa.swiftshift.presentation.features.gig_detail

import com.daffa.swiftshift.data.remote.response.GigDetailResponse

data class GigDetailState(
    val data: GigDetailResponse? = null,
    val isLoading: Boolean = false
)
