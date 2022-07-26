package com.dnovaes.pokemontcg.singleCard.domain.repository

import com.dnovaes.pokemontcg.singleCard.data.remote.network.PokemonTcgAPIInterface
import com.dnovaes.pokemontcg.singleCard.domain.model.TcgCardServerResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

interface TcgRepositoryInterface {
    suspend fun requestCard(id: String): Flow<TcgCardServerResponse>
}

class PokemonTcgRepository(
    private val tcgApiInterface: PokemonTcgAPIInterface
): TcgRepositoryInterface {

    override suspend fun requestCard(id: String): Flow<TcgCardServerResponse> {
        return flow {
            kotlin.runCatching {
                tcgApiInterface.getCard(id)
            }.onFailure {
                println("logd Failure during api request: ${it.stackTraceToString()}")
            }.onSuccess {
                emit(it)
            }
        }.flowOn(Dispatchers.IO)
    }

}
