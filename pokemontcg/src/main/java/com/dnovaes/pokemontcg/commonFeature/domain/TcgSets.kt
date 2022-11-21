package com.dnovaes.pokemontcg.commonFeature.domain

import com.dnovaes.commons.data.model.UIModelInterface

interface TcgSetsInterface: UIModelInterface {
    val selectedId: String?
    val collection: List<TcgSetInterface>
}

data class TcgSets(
    override val selectedId: String? = null,
    override val collection: List<TcgSetInterface>,
): TcgSetsInterface