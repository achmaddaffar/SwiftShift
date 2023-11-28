package com.daffa.swiftshift.domain.use_case.gig_worker

import com.daffa.swiftshift.domain.repository.IGigWorkerRepository
import com.daffa.swiftshift.util.SimpleResource
import kotlinx.coroutines.flow.Flow

class LoginGigWorkerUseCase(
    private val repository: IGigWorkerRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String
    ): Flow<SimpleResource> {
        return repository.login(
            email,
            password
        )
    }
}