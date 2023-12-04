package com.daffa.swiftshift.domain.use_case.auth

import com.daffa.swiftshift.domain.repository.IAuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val repository: IAuthRepository
) {

    operator fun invoke() {
        return repository.logout()
    }
}