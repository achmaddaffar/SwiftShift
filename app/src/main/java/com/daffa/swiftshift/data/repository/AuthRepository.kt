package com.daffa.swiftshift.data.repository

import android.content.SharedPreferences
import com.daffa.swiftshift.R
import com.daffa.swiftshift.data.remote.api.AuthApi
import com.daffa.swiftshift.domain.repository.IAuthRepository
import com.daffa.swiftshift.util.Constants
import com.daffa.swiftshift.util.Constants.Empty
import com.daffa.swiftshift.util.Resource
import com.daffa.swiftshift.util.Role
import com.daffa.swiftshift.util.SimpleResource
import com.daffa.swiftshift.util.UiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class AuthRepository(
    private val api: AuthApi,
    private val sharedPreferences: SharedPreferences
): IAuthRepository {
    override suspend fun authenticate(): Flow<SimpleResource> = flow {
        try {
            val token = sharedPreferences.getString(Constants.KEY_JWT_TOKEN, String.Empty)
            api.authenticate("Bearer $token")
            emit(Resource.Success(Unit))
        } catch (e: IOException) {
            emit(Resource.Error(UiText.StringResource(R.string.error_something_went_wrong)))
        } catch (e: HttpException) {
            emit(Resource.Error(UiText.StringResource(R.string.error_couldnt_reach_server)))
        }
    }

    override fun logout() {
        sharedPreferences.edit()
            .clear()
            .apply()
    }

    override fun getRole(): Role? {
        return when(sharedPreferences.getString(Constants.KEY_ROLE, String.Empty)) {
            Constants.GIG_WORKER -> Role.GigWorker
            Constants.GIG_PROVIDER -> Role.GigProvider
            else -> null
        }
    }
}