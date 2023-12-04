package com.daffa.swiftshift.domain.use_case.gig

import com.daffa.swiftshift.domain.model.Gig
import com.daffa.swiftshift.domain.repository.IGigRepository
import com.daffa.swiftshift.util.Resource
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecommendedGigsUseCase @Inject constructor(
    private val repository: IGigRepository,
) {
    suspend operator fun invoke(
        latLng: LatLng,
    ): Flow<Resource<List<Gig>>> {
        return repository.getRecommendedGigs(latLng)
    }
}