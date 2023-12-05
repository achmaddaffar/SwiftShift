package com.daffa.swiftshift.domain.use_case.gig

import com.daffa.swiftshift.data.remote.response.GigDetailResponse
import com.daffa.swiftshift.domain.repository.IGigRepository
import com.daffa.swiftshift.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGigDetailUseCase @Inject constructor(
    private val repository: IGigRepository
) {

    suspend operator fun invoke(gigId: String): Flow<Resource<GigDetailResponse>> {
        return repository.getGigDetail(gigId)
    }
}