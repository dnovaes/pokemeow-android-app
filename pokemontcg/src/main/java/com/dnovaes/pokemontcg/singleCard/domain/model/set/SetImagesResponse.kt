package com.dnovaes.pokemontcg.singleCard.domain.model.set

import kotlinx.serialization.Serializable

interface SetImagesResponseInterface {
    val symbol: String
    val logo: String
}

@Serializable
data class SetImagesResponse(
    override val symbol: String,
    override val logo: String
): SetImagesResponseInterface
