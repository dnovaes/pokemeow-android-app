package com.dnovaes.pokemontcg.commonFeature.domain

import com.dnovaes.commons.data.model.UIModelInterface

interface TcgSetsInterface: UIModelInterface {
    val selected: String?
    val collection: List<TcgSetInterface>
}

class TcgSets(
    override val selected: String? = null,
    override val collection: List<TcgSetInterface>
): TcgSetsInterface