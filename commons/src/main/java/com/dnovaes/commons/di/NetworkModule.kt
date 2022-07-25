package com.dnovaes.commons.di

import com.dnovaes.commons.data.network.LoggerInterceptor
import com.dnovaes.commons.data.network.PokeLoggerInterceptorInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.OkHttpClient

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun providesLoggerInterceptor(): PokeLoggerInterceptorInterface {
        return LoggerInterceptor()
    }

    @Provides
    fun providesClient(
        loggerInterceptor: PokeLoggerInterceptorInterface
    ): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(loggerInterceptor as Interceptor)
            .build()
    }

    @Provides
    fun providesJsonKotlinSerializationConfig(): Json {
        return Json { ignoreUnknownKeys = true }
    }

}