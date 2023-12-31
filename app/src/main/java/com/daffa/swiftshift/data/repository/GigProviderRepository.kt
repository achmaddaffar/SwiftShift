package com.daffa.swiftshift.data.repository

import android.content.SharedPreferences
import android.net.Uri
import androidx.core.net.toFile
import com.daffa.swiftshift.data.remote.api.GigProviderApi
import com.daffa.swiftshift.data.remote.request.CreateGigWorkerRequest
import com.daffa.swiftshift.data.remote.request.LoginRequest
import com.daffa.swiftshift.data.remote.response.GigProviderResponse
import com.daffa.swiftshift.domain.repository.IGigProviderRepository
import com.daffa.swiftshift.util.Constants
import com.daffa.swiftshift.util.Constants.Empty
import com.daffa.swiftshift.util.Resource
import com.daffa.swiftshift.util.SimpleResource
import com.daffa.swiftshift.util.UiText
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException

class GigProviderRepository(
    private val api: GigProviderApi,
    private val gson: Gson,
    private val sharedPreferences: SharedPreferences,
) : IGigProviderRepository {

    override suspend fun register(
        fullName: String,
        email: String,
        password: String,
        imageUri: Uri?,
    ): Flow<SimpleResource> = flow {
        emit(Resource.Loading())
        val request = CreateGigWorkerRequest(
            fullName = fullName,
            email = email,
            password = password
        )
        val file = imageUri?.toFile()

        try {
            val response = file?.let {
                api.register(
                    postData = MultipartBody.Part
                        .createFormData(
                            name = Constants.CREATE_ACCOUNT_PART_DATA,
                            value = gson.toJson(request)
                        ),
                    postImage = MultipartBody.Part
                        .createFormData(
                            name = Constants.PROFILE_PICTURE_DATA,
                            filename = it.name,
                            body = it.asRequestBody()
                        )
                )
            } ?: kotlin.run {
                api.register(
                    postData = MultipartBody.Part
                        .createFormData(
                            name = Constants.CREATE_ACCOUNT_PART_DATA,
                            value = gson.toJson(request)
                        )
                )
            }
            if (response.successful) {
                emit(Resource.Success(Unit))
            } else {
                response.message?.let { msg ->
                    emit(Resource.Error(UiText.DynamicString(msg)))
                } ?: emit(Resource.Error(UiText.unknownError()))
            }
        } catch (e: HttpException) {
            emit(Resource.Error(UiText.DynamicString(e.toString())))
        } catch (e: Exception) {
            emit(Resource.Error(UiText.DynamicString(e.toString())))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun login(
        email: String,
        password: String,
    ): Flow<SimpleResource> = flow {
        emit(Resource.Loading())
        val request = LoginRequest(
            email = email,
            password = password
        )

        try {
            val response = api.login(request)
            if (response.successful) {
                response.data?.token?.let { token ->
                    sharedPreferences.edit()
                        .putString(Constants.KEY_JWT_TOKEN, token)
                        .putString(Constants.KEY_ROLE, Constants.GIG_PROVIDER)
                        .putString(Constants.KEY_EMAIL, email)
                        .apply()
                }
                emit(Resource.Success(Unit))
            } else {
                response.message?.let { msg ->
                    emit(Resource.Error(UiText.DynamicString(msg)))
                } ?: emit(Resource.Error(UiText.unknownError()))
            }
        } catch (e: HttpException) {
            emit(Resource.Error(UiText.DynamicString(e.toString())))
        } catch (e: Exception) {
            emit(Resource.Error(UiText.DynamicString(e.toString())))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getProfileByEmail(): Flow<Resource<GigProviderResponse>> = flow {
        emit(Resource.Loading())
        try {
            val token =
                sharedPreferences.getString(Constants.KEY_JWT_TOKEN, String.Empty).toString()
            val email = sharedPreferences.getString(Constants.KEY_EMAIL, String.Empty).toString()
            val response = api.getProfileByEmail(
                token = "Bearer $token",
                email = email
            )
            if (response.successful) {
                emit(Resource.Success(response.data))
            } else {
                response.message?.let { msg ->
                    emit(Resource.Error(UiText.DynamicString(msg)))
                } ?: emit(Resource.Error(UiText.unknownError()))
            }
        } catch (e: HttpException) {
            emit(Resource.Error(UiText.DynamicString(e.toString())))
        } catch (e: Exception) {
            emit(Resource.Error(UiText.DynamicString(e.toString())))
        }
    }.flowOn(Dispatchers.IO)
}