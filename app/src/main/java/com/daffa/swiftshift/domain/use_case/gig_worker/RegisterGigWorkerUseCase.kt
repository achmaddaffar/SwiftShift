package com.daffa.swiftshift.domain.use_case.gig_worker

import android.net.Uri
import com.daffa.swiftshift.domain.repository.IGigWorkerRepository
import com.daffa.swiftshift.util.SimpleResource
import kotlinx.coroutines.flow.Flow

class RegisterGigWorkerUseCase(
    private val repository: IGigWorkerRepository
) {
    suspend operator fun invoke(
        fullName: String,
        email: String,
        password: String,
        imageUri: Uri?
    ): Flow<SimpleResource> {
        return repository.register(
            fullName = fullName,
            email = email,
            password = password,
            imageUri = imageUri
        )
    }
}