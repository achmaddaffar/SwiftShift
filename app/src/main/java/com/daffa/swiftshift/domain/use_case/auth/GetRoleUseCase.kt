package com.daffa.swiftshift.domain.use_case.auth

import com.daffa.swiftshift.domain.repository.IAuthRepository
import com.daffa.swiftshift.util.Role
import javax.inject.Inject

class GetRoleUseCase @Inject constructor(
    private val repository: IAuthRepository
) {

    operator fun invoke(): Role? {
        return repository.getRole()
    }
}