package com.dnovaes.pokemontcg.commonFeature.repository

import com.dnovaes.commons.data.model.ServerResponseInterface
import com.dnovaes.commons.data.network.DispatcherInterface
import com.dnovaes.pokemontcg.singleCard.data.remote.network.PokemonTcgAPIInterface
import com.dnovaes.pokemontcg.singleCard.domain.model.CardResponse
import com.dnovaes.pokemontcg.singleCard.domain.model.set.SetSearchResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

interface TcgRepositoryInterface {
    suspend fun requestCard(id: String): Flow<Result<CardResponse>>
    suspend fun requestSets(): Flow<Result<SetSearchResponse>>
}

class PokemonTcgRepository(
    private val tcgApiInterface: PokemonTcgAPIInterface,
    private val dispatcher: DispatcherInterface
): TcgRepositoryInterface {

    override suspend fun requestSets(): Flow<Result<SetSearchResponse>> {
        return flow {
            runCatching {
                tcgApiInterface.getAllSets("name,id,images")
            }.onFailure {
                emit(processErrorResponse<SetSearchResponse>(it))
            }.onSuccess {
                emit(Result.success(it))
            }
        }.flowOn(dispatcher.io)
    }

    override suspend fun requestCard(id: String): Flow<Result<CardResponse>> {
        return flow {
            runCatching {
                tcgApiInterface.getCard(id)
            }.onFailure {
                emit(processErrorResponse<CardResponse>(it))
            }.onSuccess {
                emit(Result.success(it))
            }
        }.flowOn(dispatcher.io)
    }

    private fun <T: ServerResponseInterface> processErrorResponse(throwable: Throwable): Result<T> {
        println("logd Failure during api request: ${throwable.stackTraceToString()}")
        return Result.failure(throwable)
    }

}
