package com.daffa.swiftshift.domain.use_case.gig

import androidx.paging.PagingData
import androidx.paging.map
import com.daffa.swiftshift.data.local.mapper.toGig
import com.daffa.swiftshift.domain.model.Gig
import com.daffa.swiftshift.domain.repository.IGigRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetNearbyGigsUseCase @Inject constructor(
    private val repository: IGigRepository,
) {

    operator fun invoke(
        latitude: Double,
        longitude: Double,
    ): Flow<PagingData<Gig>> {
        return repository.getNearbyGigs(
            latitude = latitude,
            longitude = longitude
        ).map { pagingData ->
            pagingData.map { it.toGig() }
        }
    }
}