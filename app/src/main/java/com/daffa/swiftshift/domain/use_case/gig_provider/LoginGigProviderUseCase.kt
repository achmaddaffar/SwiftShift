package com.daffa.swiftshift.domain.use_case.gig_provider

import com.daffa.swiftshift.domain.repository.IGigProviderRepository
import com.daffa.swiftshift.util.SimpleResource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginGigProviderUseCase @Inject constructor(
    private val repository: IGigProviderRepository,
) {

    suspend operator fun invoke(
        email: String,
        password: String,
    ): Flow<SimpleResource> {
        return repository.login(
            email,
            password
        )
    }
}