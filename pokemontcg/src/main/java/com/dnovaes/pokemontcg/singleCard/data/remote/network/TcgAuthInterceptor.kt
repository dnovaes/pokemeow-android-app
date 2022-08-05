package com.dnovaes.pokemontcg.singleCard.data.remote.network

import com.dnovaes.pokemontcg.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

interface TcgAuthInterceptorInterface: Interceptor

class TcgAuthInterceptor: TcgAuthInterceptorInterface {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("X-Api-Key", BuildConfig.POKETCG_API_KEY)
            .build()
        return chain.proceed(request)
    }
}