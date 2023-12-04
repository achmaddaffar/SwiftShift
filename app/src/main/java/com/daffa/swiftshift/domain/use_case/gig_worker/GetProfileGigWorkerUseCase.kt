package com.daffa.swiftshift.domain.use_case.gig_worker

import com.daffa.swiftshift.data.remote.response.GigWorkerResponse
import com.daffa.swiftshift.domain.repository.IGigWorkerRepository
import com.daffa.swiftshift.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProfileGigWorkerUseCase @Inject constructor(
    private val repository: IGigWorkerRepository,
) {

    suspend operator fun invoke(): Flow<Resource<GigWorkerResponse>> {
        return repository.getProfileByEmail()
    }
}