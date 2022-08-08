package com.dnovaes.pokemeow.data

import retrofit2.Retrofit


abstract class BaseClientBuilder {
    abstract val baseUrl: String

    abstract fun buildApiClient(retrofit: Retrofit): BaseServiceApi

}
