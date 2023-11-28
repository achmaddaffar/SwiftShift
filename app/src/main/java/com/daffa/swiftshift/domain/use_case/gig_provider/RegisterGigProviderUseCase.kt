package com.daffa.swiftshift.domain.use_case.gig_provider

import android.net.Uri
import com.daffa.swiftshift.domain.repository.IGigProviderRepository
import com.daffa.swiftshift.util.SimpleResource
import kotlinx.coroutines.flow.Flow

class RegisterGigProviderUseCase(
    private val repository: IGigProviderRepository
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