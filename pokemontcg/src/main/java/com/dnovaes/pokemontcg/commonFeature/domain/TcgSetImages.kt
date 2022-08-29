package com.dnovaes.pokemontcg.commonFeature.domain

interface TcgSetImagesInterface {
    val symbol: String
    val logo: String
}

class TcgSetImages(
    override val symbol: String,
    override val logo: String
): TcgSetImagesInterface
