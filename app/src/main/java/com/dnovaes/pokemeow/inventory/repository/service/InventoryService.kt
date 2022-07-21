package com.dnovaes.pokemeow.inventory.repository.service

import com.dnovaes.pokemeow.data.BaseServiceApi
import com.dnovaes.pokemeow.inventory.repository.data.Data
import retrofit2.http.GET

interface InventoryService : BaseServiceApi {

    companion object {
        val baseUrl: String
            get() = "https://retoolapi.dev/uW7Yyx/"

    }

    @GET("inventory")
    suspend fun getInventory(): Data
    //suspend fun getInventory(): NetworkResponse<Data>
}
