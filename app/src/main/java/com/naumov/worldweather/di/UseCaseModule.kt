package com.naumov.worldweather.di

import android.content.Context
import com.naumov.worldweather.domain.usecase.LocationNameProviderUseCase
import com.naumov.worldweather.domain.usecase.LocationNameProviderUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetLocationNameUseCase(@ApplicationContext context: Context): LocationNameProviderUseCase {
        return LocationNameProviderUseCaseImpl(context)
    }
}
