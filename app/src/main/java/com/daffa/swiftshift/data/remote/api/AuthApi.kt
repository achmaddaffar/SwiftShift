package com.daffa.swiftshift.data.remote.api

import com.daffa.swiftshift.util.Constants
import retrofit2.http.GET
import retrofit2.http.Header

interface AuthApi {

    @GET("/api/authenticate")
    suspend fun authenticate(
        @Header("Authorization") token: String
    )

    companion object {
        const val BASE_URL = Constants.BASE_URL
    }
}