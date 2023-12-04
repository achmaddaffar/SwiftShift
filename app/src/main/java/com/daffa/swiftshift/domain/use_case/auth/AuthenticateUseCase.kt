package com.daffa.swiftshift.domain.use_case.auth

import com.daffa.swiftshift.domain.repository.IAuthRepository
import com.daffa.swiftshift.util.SimpleResource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenticateUseCase @Inject constructor(
    private val repository: IAuthRepository
) {

    suspend operator fun invoke(): Flow<SimpleResource> {
        print("gigs auth")
        return repository.authenticate()
    }
}