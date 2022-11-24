package com.dnovaes.pokemontcg.singleCard.domain.model

import com.dnovaes.commons.data.model.ServerResponseInterface
import kotlinx.serialization.Serializable

interface CardDataResponseInterface: ServerResponseInterface {
    val id: String
    val name: String
    val hp: String?
    val images: CardImagesResponseInterface
}

@Serializable
data class CardDataResponse(
    override val id: String,
    override val name: String,
    override val hp: String? = null,
    override val images: CardImagesResponse
): CardDataResponseInterface
