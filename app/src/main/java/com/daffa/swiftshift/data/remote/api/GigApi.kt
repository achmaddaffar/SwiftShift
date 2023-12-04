package com.daffa.swiftshift.data.remote.api

import com.daffa.swiftshift.data.remote.response.BasicApiResponse
import com.daffa.swiftshift.data.remote.dto.GigDto
import com.daffa.swiftshift.util.Constants
import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface GigApi {

    @Multipart
    @POST("/api/gig/create")
    fun createGig(
        @Part postData: MultipartBody.Part,
        @Part postImage: MultipartBody.Part? = null,
    ): BasicApiResponse<Unit>

    @GET("/api/gig/get_nearby")
    suspend fun getNearbyGigs(
        @Header("Authorization") token: String,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int = Constants.DEFAULT_NEARBY_GIGS_PAGE_SIZE
    ): BasicApiResponse<List<GigDto>>

    @GET("/api/gig/get_recommended")
    suspend fun getRecommendedGigs(
        @Header("Authorization") token: String,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double
    ): BasicApiResponse<List<GigDto>>

    companion object {
        const val BASE_URL = Constants.BASE_URL
    }
}