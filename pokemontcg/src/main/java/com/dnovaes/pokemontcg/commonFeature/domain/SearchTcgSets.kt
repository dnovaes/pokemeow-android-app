package com.dnovaes.pokemontcg.commonFeature.domain

import com.dnovaes.commons.data.model.UIModelInterface

interface SearchTcgSetsInterface: UIModelInterface {
    val selected: String?
    val result: List<TcgSetInterface>
}

class SearchTcgSets(
    override val selected: String? = null,
    override val result: List<TcgSetInterface>
): SearchTcgSetsInterface