package com.daffa.swiftshift.di

import android.content.SharedPreferences
import com.daffa.swiftshift.data.remote.api.GigProviderApi
import com.daffa.swiftshift.data.repository.GigProviderRepository
import com.daffa.swiftshift.domain.repository.IGigProviderRepository
import com.daffa.swiftshift.domain.use_case.gig_provider.LoginGigProviderUseCase
import com.daffa.swiftshift.domain.use_case.gig_provider.RegisterGigProviderUseCase
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
object GigProviderModule {

    @Provides
    @Singleton
    fun provideGigProviderApi(): GigProviderApi {
        return Retrofit.Builder()
            .baseUrl(GigProviderApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GigProviderApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGigProviderRepository(
        api: GigProviderApi,
        gson: Gson,
        sharedPreferences: SharedPreferences,
    ): IGigProviderRepository = GigProviderRepository(api, gson, sharedPreferences)


    @Provides
    fun provideRegisterGigProviderUseCase(
        repository: IGigProviderRepository,
    ): RegisterGigProviderUseCase = RegisterGigProviderUseCase(repository)


    @Provides
    fun provideLoginGigProviderUseCase(
        repository: IGigProviderRepository,
    ): LoginGigProviderUseCase = LoginGigProviderUseCase(repository)
}