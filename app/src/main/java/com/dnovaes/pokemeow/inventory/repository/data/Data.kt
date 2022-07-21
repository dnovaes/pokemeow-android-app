package com.dnovaes.pokemeow.inventory.repository.data

import kotlinx.serialization.Serializable

@Serializable
data class Data(
   override val data: List<DataGroup>
): DataInterface {
   companion object {
      fun mockedData(): DataInterface {
         return Data(
            listOf(
               DataGroup(
                  title = "currencies",
                  content = DataItem("dolar", 100)
               )
            )
         )
      }
   }
}

interface DataInterface: Response {
   val data: List<DataGroupInterface>
}
