package com.dnovaes.pokemontcg.singleCard.domain.model

import com.dnovaes.commons.data.model.ServerResponseInterface
import kotlinx.serialization.Serializable

interface CardImagesResponseInterface: ServerResponseInterface {
    val large: String
    val small: String
}

@Serializable
data class CardImagesResponse(
    override val large: String,
    override val small: String
): CardImagesResponseInterface
