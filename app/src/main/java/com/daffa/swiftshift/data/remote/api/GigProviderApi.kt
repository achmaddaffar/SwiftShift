package com.daffa.swiftshift.data.remote.api

import com.daffa.swiftshift.data.remote.request.LoginRequest
import com.daffa.swiftshift.data.remote.response.AuthResponse
import com.daffa.swiftshift.data.remote.response.BasicApiResponse
import com.daffa.swiftshift.data.remote.response.GigProviderResponse
import com.daffa.swiftshift.util.Constants
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface GigProviderApi {

    @Multipart
    @POST("/api/gig_provider/create")
    suspend fun register(
        @Part postData: MultipartBody.Part,
        @Part postImage: MultipartBody.Part? = null,
    ): BasicApiResponse<Unit>

    @POST("/api/gig_provider/login")
    suspend fun login(
        @Body request: LoginRequest,
    ): BasicApiResponse<AuthResponse>

    @GET("/api/gig_provider/profile_by_email")
    suspend fun getProfileByEmail(
        @Header("Authorization") token: String,
        @Query("email") email: String,
    ): BasicApiResponse<GigProviderResponse>

    companion object {
        const val BASE_URL = Constants.BASE_URL
    }
}