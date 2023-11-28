package com.daffa.swiftshift.domain.repository

import android.net.Uri
import com.daffa.swiftshift.util.SimpleResource
import kotlinx.coroutines.flow.Flow

interface IGigWorkerRepository {

    suspend fun register(
        fullName: String,
        email: String,
        password: String,
        imageUri: Uri?
    ): Flow<SimpleResource>

    suspend fun login(
        email: String,
        password: String
    ): Flow<SimpleResource>
}