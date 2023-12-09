package com.daffa.swiftshift.domain.repository

import com.daffa.swiftshift.util.Role
import com.daffa.swiftshift.util.SimpleResource
import kotlinx.coroutines.flow.Flow

interface IAuthRepository {

    suspend fun authenticate(): Flow<SimpleResource>

    fun logout()

    fun getRole(): Role?
}