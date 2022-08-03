package com.dnovaes.pokemontcg.singleCard.domain.model.ui

import com.dnovaes.commons.data.model.UIModelInterface
import kotlinx.serialization.Serializable

interface CardInterface: UIModelInterface {
    val id: String
    val name: String
    val hp: String
    val images: CardImagesInterface
}

@Serializable
data class Card(
    override val id: String,
    override val name: String,
    override val hp: String,
    override val images: CardImages
) : CardInterface
