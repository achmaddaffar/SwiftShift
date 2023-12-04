package com.daffa.swiftshift.presentation.features.search

import com.daffa.swiftshift.domain.model.Gig

data class SearchGigState(
    val gigItems: List<Gig> = emptyList(),
    val isLoading: Boolean = false
)