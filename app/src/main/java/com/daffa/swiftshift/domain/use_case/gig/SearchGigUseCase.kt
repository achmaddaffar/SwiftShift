package com.daffa.swiftshift.domain.use_case.gig

import com.daffa.swiftshift.domain.model.Gig
import com.daffa.swiftshift.domain.repository.IGigRepository
import com.daffa.swiftshift.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchGigUseCase @Inject constructor(
    private val repository: IGigRepository
) {

    suspend operator fun invoke(query: String): Flow<Resource<List<Gig>>> {
        if (query.isBlank() || query.isEmpty())
            return flow {  }
        return repository.searchGig(query)
    }
}