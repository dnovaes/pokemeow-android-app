package com.dnovaes.pokemontcg.singleCard.repository

import com.dnovaes.pokemontcg.singleCard.network.PokemonTcgAPI

class PokemonTcgRepository(
    private val tcgApi: PokemonTcgAPI
) {

    suspend fun requestCard(id: String) {
        tcgApi.getCard(id)
    }
}