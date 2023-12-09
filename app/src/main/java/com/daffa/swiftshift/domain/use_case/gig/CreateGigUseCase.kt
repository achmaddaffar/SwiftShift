package com.daffa.swiftshift.domain.use_case.gig

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.daffa.swiftshift.data.remote.request.CreateGigRequest
import com.daffa.swiftshift.domain.repository.IGigRepository
import com.daffa.swiftshift.util.SimpleResource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateGigUseCase @Inject constructor(
    private val repository: IGigRepository
): ViewModel() {

    suspend operator fun invoke(
        request: CreateGigRequest,
        imageUri: Uri?
    ): Flow<SimpleResource> {
        return repository.createGig(
            request,
            imageUri
        )
    }
}