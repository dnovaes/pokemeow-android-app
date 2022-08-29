package com.dnovaes.pokemontcg.singleCard.domain.model.set

import kotlinx.serialization.Serializable

interface SetResponseInterface {
    val id: String
    val name: String
    val images: SetImagesResponseInterface
}

@Serializable
data class SetResponse(
    override val id: String,
    override val name: String,
    override val images: SetImagesResponse
): SetResponseInterface
