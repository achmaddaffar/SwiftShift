package com.daffa.swiftshift.di

import android.content.SharedPreferences
import com.daffa.swiftshift.data.remote.api.AuthApi
import com.daffa.swiftshift.data.repository.AuthRepository
import com.daffa.swiftshift.domain.repository.IAuthRepository
import com.daffa.swiftshift.domain.use_case.auth.AuthenticateUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthApi(): AuthApi {
        return Retrofit.Builder()
            .baseUrl(AuthApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        api: AuthApi,
        sharedPreferences: SharedPreferences
    ): IAuthRepository {
        return AuthRepository(api, sharedPreferences)
    }
}