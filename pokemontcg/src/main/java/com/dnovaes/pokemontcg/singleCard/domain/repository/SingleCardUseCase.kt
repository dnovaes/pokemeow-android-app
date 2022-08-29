package com.dnovaes.pokemontcg.singleCard.domain.repository

import com.dnovaes.pokemontcg.commonFeature.domain.TcgSet
import com.dnovaes.pokemontcg.commonFeature.domain.TcgSetInterface
import com.dnovaes.pokemontcg.commonFeature.repository.TcgRepositoryInterface
import com.dnovaes.pokemontcg.singleCard.domain.model.ui.CardInterface
import com.dnovaes.pokemontcg.commonFeature.repository.mapper.TcgMapperInterface
import com.dnovaes.pokemontcg.singleCard.domain.repository.mapper.SingleCardMapperInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface SingleCardUseCaseInterface {
    suspend fun requestSets(): Flow<Result<List<TcgSetInterface>>>
    suspend fun requestCard(id: String): Flow<Result<CardInterface>>
}

class SingleCardUseCase(
    private val pkmTcgRepository: TcgRepositoryInterface,
    private val singleCardMapper: SingleCardMapperInterface,
    private val tcgMapper: TcgMapperInterface,
): SingleCardUseCaseInterface {

    override suspend fun requestSets(): Flow<Result<List<TcgSetInterface>>> {
        return flow {
            pkmTcgRepository.requestSets().collect { result ->
                if (result.isSuccess) {
                    result.getOrNull()?.let { searchResponse ->
                        val modelUi = tcgMapper.mapSet(searchResponse)
                        emit(Result.success(modelUi))
                    }
                } else {
                    try {
                        emit(result as Result<List<TcgSet>>)
                    } catch (e: Exception) {
                        println("Exception on cast result.failure ${::requestSets.name}]")
                    }
                }
            }
        }
    }

    override suspend fun requestCard(id: String): Flow<Result<CardInterface>> {
        return flow {
                pkmTcgRepository.requestCard(id).collect {
                    if (it.isSuccess) {
                        val modelUi = singleCardMapper.mapCard(it.getOrNull()!!)
                        emit(Result.success(modelUi))
                    } else {
                        try {
                            emit(it as Result<CardInterface>)
                        } catch (e: Exception) {
                            println("Exception on cast result.failure ${::requestCard.name}")
                        }
                    }
                }
            }
        }
}
