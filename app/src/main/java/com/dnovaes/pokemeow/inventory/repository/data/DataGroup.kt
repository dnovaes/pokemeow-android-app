package com.dnovaes.pokemeow.inventory.repository.data

import kotlinx.serialization.Serializable

@Serializable
data class DataGroup(
   override val title: String,
   override val content: DataItem
): DataGroupInterface

interface DataGroupInterface: Response {
   val title: String
   val content: DataItemInterface
}
