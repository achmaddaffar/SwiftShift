package com.daffa.swiftshift.di

import android.content.Context
import android.content.SharedPreferences
import com.daffa.swiftshift.data.repository.LocationRepository
import com.daffa.swiftshift.domain.repository.ILocationRepository
import com.daffa.swiftshift.util.Constants
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideSharedPreference(
        @ApplicationContext context: Context
    ): SharedPreferences {
        return context.getSharedPreferences(Constants.PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    fun provideLocationClient(
        @ApplicationContext context: Context
    ): ILocationRepository = LocationRepository(
        context,
        LocationServices.getFusedLocationProviderClient(context)
    )
}