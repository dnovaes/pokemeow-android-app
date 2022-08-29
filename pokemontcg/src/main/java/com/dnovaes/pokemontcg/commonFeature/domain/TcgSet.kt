package com.dnovaes.pokemontcg.commonFeature.domain

import com.dnovaes.commons.data.model.UIModelInterface

interface TcgSetInterface: UIModelInterface {
    val id: String
    val name: String
    val images: TcgSetImagesInterface
}

class TcgSet(
    override val id: String,
    override val name: String,
    override val images: TcgSetImages
): TcgSetInterface