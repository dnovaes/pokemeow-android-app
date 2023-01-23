package com.dnovaes.pokemontcg.commonFeature.domain

import com.dnovaes.commons.data.model.UIModelInterface

interface TcgSetInterface: UIModelInterface {
    val idName: String
    val title: String
    val images: TcgSetImagesInterface
}

class TcgSet(
    override val idName: String,
    override val title: String,
    override val images: TcgSetImages
): TcgSetInterface {
    override fun toString(): String = "{$idName, $title}"
}