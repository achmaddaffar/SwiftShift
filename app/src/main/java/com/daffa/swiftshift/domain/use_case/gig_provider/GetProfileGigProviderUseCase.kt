package com.daffa.swiftshift.domain.use_case.gig_provider

import com.daffa.swiftshift.data.remote.response.GigProviderResponse
import com.daffa.swiftshift.domain.repository.IGigProviderRepository
import com.daffa.swiftshift.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProfileGigProviderUseCase @Inject constructor(
    private val repository: IGigProviderRepository
) {

    suspend operator fun invoke(): Flow<Resource<GigProviderResponse>> {
        return repository.getProfileByEmail()
    }
}