package com.daffa.swiftshift.data.remote.api

import com.daffa.swiftshift.data.remote.request.LoginRequest
import com.daffa.swiftshift.data.remote.response.AuthResponse
import com.daffa.swiftshift.data.remote.response.BasicApiResponse
import com.daffa.swiftshift.util.Constants
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface GigProviderApi {

    @Multipart
    @POST("/api/gig_provider/create")
    suspend fun register(
        @Part postData: MultipartBody.Part,
        @Part postImage: MultipartBody.Part? = null
    ): BasicApiResponse<Unit>

    @POST("/api/gig_provider/login")
    suspend fun login(
        @Body request: LoginRequest
    ): BasicApiResponse<AuthResponse>

    companion object {
        const val BASE_URL = Constants.BASE_URL
    }
}