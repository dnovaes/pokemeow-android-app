package com.dnovaes.pokemontcg.singleCard.data.di

import com.dnovaes.pokemontcg.singleCard.data.remote.network.PokemonTcgAPIInterface
import com.dnovaes.pokemontcg.singleCard.data.remote.network.TCG_SERVICE_URL
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class TcgNetworkDataModule {

    companion object {
        const val TCG_RETROFIT = "tcgRetrofit"
    }

    @Named(TCG_RETROFIT)
    @Provides
    fun providesRetrofit(
        okHttpClient: OkHttpClient,
        json: Json
    ) : Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(TCG_SERVICE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun providesTcgService(
        @Named(TCG_RETROFIT) retrofit: Retrofit
    ) : PokemonTcgAPIInterface {
        return retrofit.create(PokemonTcgAPIInterface::class.java)
    }
}