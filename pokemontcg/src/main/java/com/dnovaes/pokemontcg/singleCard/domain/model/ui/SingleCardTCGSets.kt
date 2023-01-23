package com.dnovaes.pokemontcg.singleCard.domain.model.ui

import com.dnovaes.commons.data.model.UIModelInterface
import com.dnovaes.pokemontcg.commonFeature.domain.TcgSetInterface

interface SingleCardSetsInterface: UIModelInterface {
    val selectedIdName: String?
    val collection: List<TcgSetInterface>
}

data class SingleCardTCGSets(
    override val selectedIdName: String? = null,
    override val collection: List<TcgSetInterface>,
): SingleCardSetsInterface