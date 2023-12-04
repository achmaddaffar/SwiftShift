package com.daffa.swiftshift.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.daffa.swiftshift.data.local.db.GigDatabase
import com.daffa.swiftshift.data.remote.api.GigApi
import com.daffa.swiftshift.data.repository.GigRepository
import com.daffa.swiftshift.domain.repository.IGigRepository
import com.daffa.swiftshift.domain.use_case.gig.GetNearbyGigsUseCase
import com.daffa.swiftshift.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GigModule {

    @Provides
    @Singleton
    fun provideGigDatabase(@ApplicationContext context: Context): GigDatabase {
        return Room.databaseBuilder(
            context,
            GigDatabase::class.java,
            Constants.GIG_DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideGigApi(): GigApi {
        return Retrofit.Builder()
            .baseUrl(GigApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GigApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGigRepository(
        gigApi: GigApi,
        gigDb: GigDatabase,
        sharedPreferences: SharedPreferences
    ): IGigRepository = GigRepository(gigApi, gigDb, sharedPreferences)
}