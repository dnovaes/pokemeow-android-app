package com.dnovaes.commons.data.di

import com.dnovaes.commons.data.network.DispatcherInterface
import com.dnovaes.commons.data.network.LoggerInterceptor
import com.dnovaes.commons.data.network.PokeLoggerInterceptorInterface
import com.dnovaes.commons.data.network.TcgDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun providesLoggerInterceptor(): PokeLoggerInterceptorInterface {
        return LoggerInterceptor()
    }

    @Provides
    fun providesJsonKotlinSerializationConfig(): Json {
        return Json { ignoreUnknownKeys = true }
    }

    @Provides
    fun providesDispatcher(): DispatcherInterface = TcgDispatcher()

}