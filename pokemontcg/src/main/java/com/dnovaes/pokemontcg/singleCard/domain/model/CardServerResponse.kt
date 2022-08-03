package com.dnovaes.pokemontcg.singleCard.domain.model

import com.dnovaes.commons.data.model.ServerResponseInterface
import kotlinx.serialization.Serializable

interface CardResponseInterface: ServerResponseInterface {
    val data: CardDataResponseInterface
}

@Serializable
data class CardResponse(
    override val data: CardDataResponse
) : CardResponseInterface
