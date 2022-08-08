package com.dnovaes.pokemeow.inventory.repository.data

import kotlinx.serialization.Serializable

@Serializable
data class DataItem(
   override val title: String,
   override val count: Int
): DataItemInterface

interface DataItemInterface: Response{
   val title: String
   val count: Int
}