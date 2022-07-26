package com.dnovaes.pokemontcg.singleCard.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class TcgCardDataResponse(
    val id: String,
    val name: String,
    val hp: String,
    val images: TcgCardImagesResponse
)
