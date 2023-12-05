package com.daffa.swiftshift.presentation.features.nearby_gigs

import com.daffa.swiftshift.domain.model.Gig

data class NearbyGigState(
    val gigs: List<Gig> = emptyList(),
    val isLoading: Boolean = false
)
