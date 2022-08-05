package com.dnovaes.pokemontcg.singleCard.domain.repository.mapper

import com.dnovaes.pokemontcg.singleCard.domain.model.CardImagesResponseInterface
import com.dnovaes.pokemontcg.singleCard.domain.model.CardResponseInterface
import com.dnovaes.pokemontcg.singleCard.domain.model.ui.Card
import com.dnovaes.pokemontcg.singleCard.domain.model.ui.CardImages
import com.dnovaes.pokemontcg.singleCard.domain.model.ui.CardInterface

interface SingleCardMapperInterface {
    fun mapCard(singleCardResponse: CardResponseInterface): CardInterface
}

class SingleCardMapper: SingleCardMapperInterface {

    override fun mapCard(singleCardResponse: CardResponseInterface): CardInterface {
        val card = singleCardResponse.data
        return Card(card.id, card.name, card.hp, mapCardImages(card.images))
    }

    private fun mapCardImages(images: CardImagesResponseInterface): CardImages {
        return CardImages(
            images.small,
            images.large
        )
    }
}