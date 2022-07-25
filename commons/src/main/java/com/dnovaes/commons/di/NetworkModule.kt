package com.dnovaes.commons.di

import com.dnovaes.commons.data.network.LoggerInterceptor
import com.dnovaes.commons.data.network.PokeLoggerInterceptorInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun providesLoggerInterceptor(): PokeLoggerInterceptorInterface {
        return LoggerInterceptor()
    }

}