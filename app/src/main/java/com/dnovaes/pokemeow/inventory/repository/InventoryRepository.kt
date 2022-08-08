package com.dnovaes.pokemeow.inventory.repository

import com.dnovaes.pokemeow.inventory.repository.data.Data
import com.dnovaes.pokemeow.inventory.repository.data.DataInterface
import com.dnovaes.pokemeow.inventory.repository.service.InventoryService
import java.lang.Exception
import javax.inject.Inject


class InventoryRepository @Inject constructor(
    private val serviceApi: InventoryService
) {

    suspend fun getInventory(): DataInterface {
        return try {
            return serviceApi.getInventory()
        } catch (e: Exception) {
            println("logd Exception on Repository) $e")
            return Data.mockedData()
        }
/*
        if (result is NetworkResponse.Error) {
            println("logd result: ${result.message}")
        }
*/
    }
}
