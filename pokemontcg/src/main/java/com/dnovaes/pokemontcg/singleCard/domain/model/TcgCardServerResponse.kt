package com.dnovaes.pokemontcg.singleCard.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class TcgCardServerResponse(
    val data: TcgCardDataResponse,
)
