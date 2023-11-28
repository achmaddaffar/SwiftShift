package com.daffa.swiftshift.domain.use_case.auth

import com.daffa.swiftshift.domain.repository.IAuthRepository
import com.daffa.swiftshift.util.SimpleResource
import kotlinx.coroutines.flow.Flow

class AuthenticateUseCase(
    private val repository: IAuthRepository
) {

    suspend operator fun invoke(): Flow<SimpleResource> {
        return repository.authenticate()
    }
}