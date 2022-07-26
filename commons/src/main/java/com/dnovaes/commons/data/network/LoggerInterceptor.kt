package com.dnovaes.commons.data.network

import okhttp3.Interceptor
import okhttp3.Response

interface PokeLoggerInterceptorInterface

class LoggerInterceptor: Interceptor, PokeLoggerInterceptorInterface {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("X-Api-Key", "3079204f-ecb6-425e-9332-eed64e3608e1")
            .build()

        println("logd ==== REQUEST ====")
        println("logd request: $request")
        println("logd body: ${request.body}")
        println("logd headers: ${request.headers}")
        println("logd ==== request end ====")

        val response = chain.proceed(request)
        println("logd ==== RESPONSE ====")
        println("logd response: $response")
        println("logd body: ${response.body}")
        println("logd headers: ${response.headers}")
        println("logd ==== response end ====")
        return response
    }
}