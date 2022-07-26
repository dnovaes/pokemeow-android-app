package com.dnovaes.pokemontcg.singleCard.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class TcgCardImagesResponse(
    val large: String,
    val small: String
)
