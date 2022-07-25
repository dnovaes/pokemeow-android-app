package com.dnovaes.pokemontcg.singleCard.domain.repository

import com.dnovaes.pokemontcg.singleCard.data.remote.network.PokemonTcgAPIInterface

interface TcgRepositoryInterface {
    suspend fun requestCard(id: String)
}

class PokemonTcgRepository(
    private val tcgApiInterface: PokemonTcgAPIInterface
): TcgRepositoryInterface {

    override suspend fun requestCard(id: String) {
        tcgApiInterface.getCard(id)
    }

}
