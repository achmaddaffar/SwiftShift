package com.daffa.swiftshift.di

import android.content.SharedPreferences
import com.daffa.swiftshift.data.remote.api.GigWorkerApi
import com.daffa.swiftshift.data.repository.GigWorkerRepository
import com.daffa.swiftshift.domain.repository.IGigWorkerRepository
import com.daffa.swiftshift.domain.use_case.gig_worker.LoginGigWorkerUseCase
import com.daffa.swiftshift.domain.use_case.gig_worker.RegisterGigWorkerUseCase
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GigWorkerModule {

    @Provides
    @Singleton
    fun provideGigWorkerApi(): GigWorkerApi {
        return Retrofit.Builder()
            .baseUrl(GigWorkerApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GigWorkerApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGigWorkerRepository(
        api: GigWorkerApi,
        gson: Gson,
        sharedPreferences: SharedPreferences,
    ): IGigWorkerRepository = GigWorkerRepository(api, gson, sharedPreferences)


    @Provides
    @Singleton
    fun provideRegisterGigWorkerUseCase(
        repository: IGigWorkerRepository,
    ): RegisterGigWorkerUseCase = RegisterGigWorkerUseCase(repository)

    @Provides
    @Singleton
    fun provideLoginGigWorkerUseCase(
        repository: IGigWorkerRepository,
    ): LoginGigWorkerUseCase = LoginGigWorkerUseCase(repository)
}