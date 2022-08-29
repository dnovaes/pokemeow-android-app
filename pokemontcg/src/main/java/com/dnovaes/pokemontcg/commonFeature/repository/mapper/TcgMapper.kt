package com.dnovaes.pokemontcg.commonFeature.repository.mapper

import com.dnovaes.pokemontcg.commonFeature.domain.TcgSet
import com.dnovaes.pokemontcg.commonFeature.domain.TcgSetImages
import com.dnovaes.pokemontcg.commonFeature.domain.TcgSetInterface
import com.dnovaes.pokemontcg.singleCard.domain.model.set.SetSearchResponseInterface

interface TcgMapperInterface {
    fun mapSet(setResponse: SetSearchResponseInterface): List<TcgSetInterface>
}

class TcgMapper: TcgMapperInterface {

    override fun mapSet(setResponse: SetSearchResponseInterface): List<TcgSetInterface> {
        val regex = "\\w*tg\\b".toRegex(RegexOption.IGNORE_CASE)
        return setResponse.data.filter { !it.id.contains(regex) }.map {
            val tcgSetImage = TcgSetImages(it.images.symbol, it.images.logo)
            TcgSet(it.id, it.name, tcgSetImage)
        }
    }
}