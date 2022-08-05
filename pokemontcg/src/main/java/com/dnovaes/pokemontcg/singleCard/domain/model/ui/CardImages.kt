package com.dnovaes.pokemontcg.singleCard.domain.model.ui

import com.dnovaes.commons.data.model.UIModelInterface
import kotlinx.serialization.Serializable

interface CardImagesInterface: UIModelInterface {
    val large: String
    val small: String
}

@Serializable
data class CardImages(
    override val large: String,
    override val small: String
): CardImagesInterface
