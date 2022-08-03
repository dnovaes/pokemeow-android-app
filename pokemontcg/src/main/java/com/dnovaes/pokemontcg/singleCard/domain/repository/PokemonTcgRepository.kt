package com.dnovaes.pokemontcg.singleCard.domain.repository

import com.dnovaes.commons.data.network.DispatcherInterface
import com.dnovaes.pokemontcg.singleCard.data.remote.network.PokemonTcgAPIInterface
import com.dnovaes.pokemontcg.singleCard.domain.model.CardResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

interface TcgRepositoryInterface {
    suspend fun requestCard(id: String): Flow<Result<CardResponse>>
}

class PokemonTcgRepository(
    private val tcgApiInterface: PokemonTcgAPIInterface,
    private val dispatcher: DispatcherInterface
): TcgRepositoryInterface {

    override suspend fun requestCard(id: String): Flow<Result<CardResponse>> {
        return flow {
            runCatching {
                tcgApiInterface.getCard(id)
            }.onFailure {
                println("logd Failure during api request: ${it.stackTraceToString()}")
                val failure = Result.failure<CardResponse>(it)
                emit(failure)
            }.onSuccess {
                emit(Result.success(it))
            }
        }.flowOn(dispatcher.io)
    }

}
