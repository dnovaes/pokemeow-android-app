package com.dnovaes.pokemeow.di

import com.dnovaes.pokemeow.inventory.repository.service.InventoryService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun getOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        println("logd loggingInterceptor set")
        return OkHttpClient.Builder()
            //.addInterceptor(RequestInterceptor())
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun getServiceApi(retrofit: Retrofit): InventoryService {
        return retrofit.create(InventoryService::class.java)
    }

    @Provides
    @Singleton
    fun getRetrofit(client: OkHttpClient): Retrofit {
        val mediaType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(InventoryService.baseUrl)
            //.addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(Json.asConverterFactory(mediaType))
            .client(client)
            .build()
    }
}