package com.dnovaes.pokemontcg.singleCard.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnovaes.commons.data.model.UIError
import com.dnovaes.commons.data.model.uiviewstate.UIDataState
import com.dnovaes.commons.data.model.uiviewstate.UIViewState
import com.dnovaes.commons.data.model.uiviewstate.inDone
import com.dnovaes.commons.data.model.uiviewstate.withError
import com.dnovaes.commons.data.model.uiviewstate.withResult
import com.dnovaes.pokemontcg.R
import com.dnovaes.pokemontcg.singleCard.domain.model.ui.SingleCardTCGSets
import com.dnovaes.pokemontcg.commonFeature.domain.TcgSetInterface
import com.dnovaes.pokemontcg.singleCard.domain.model.ui.SingleCardSetsInterface
import com.dnovaes.pokemontcg.singleCard.data.model.SingleCardUIDataProcess
import com.dnovaes.pokemontcg.singleCard.data.model.asLoadingPkmCardSets
import com.dnovaes.pokemontcg.singleCard.data.model.asLoadingPkmSingleCard
import com.dnovaes.pokemontcg.singleCard.data.model.asPickingPkmCardSet
import com.dnovaes.pokemontcg.singleCard.domain.model.ui.CardInterface
import com.dnovaes.pokemontcg.singleCard.domain.repository.SingleCardUseCaseInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class SingleCardViewModel @Inject constructor(
    private val singleCardUseCase: SingleCardUseCaseInterface,
): ViewModel() {

    private val _cardLiveData: MutableLiveData<UIViewState<CardInterface>> = MutableLiveData()
    val cardLiveData: LiveData<UIViewState<CardInterface>> = _cardLiveData
    private val initialCardState = UIViewState<CardInterface>(
        process = SingleCardUIDataProcess.LOADING_SINGLE_CARD,
        state = UIDataState.PROCESSING
    )

    private val _setsLiveData: MutableLiveData<UIViewState<SingleCardSetsInterface>> = MutableLiveData()
    val setsLiveData: LiveData<UIViewState<SingleCardSetsInterface>> = _setsLiveData
    private val initialSetsState = UIViewState<SingleCardSetsInterface>(
        process = SingleCardUIDataProcess.LOADING_CARD_SETS,
        state = UIDataState.PROCESSING
    )

    fun getCard(cardNumber: String) {
        val currSetState = _setsLiveData.value ?: return

        val sets = currSetState.result
        val selectedExpansionSet = sets?.selectedIdName ?: sets?.collection?.firstOrNull()?.idName ?: run {
            postUnselectedSetError()
            return
        }
        _cardLiveData.postValue(initialCardState)
        viewModelScope.launch {
            val cardSetId = "$selectedExpansionSet-${cardNumber}"
            singleCardUseCase.requestCard(cardSetId).collect { result ->
                handleSingleCardResponse(result)
            }
        }
    }

    private fun postUnselectedSetError() {
        val newState = initialCardState
            .inDone()
            .asLoadingPkmSingleCard()
            .withResult(null)
            .withError(UIError(R.string.search_failure_set_not_selected))
        _cardLiveData.postValue(newState)
    }

    private fun handleSingleCardResponse(
        result: Result<CardInterface>
    ) {
        if (result.isSuccess) {
            val content = result.getOrNull()
            content?.let {
                val newState = initialCardState
                    .inDone()
                    .asLoadingPkmSingleCard()
                    .withResult(it)
                    .withError(null)
                _cardLiveData.postValue(newState)
            }
        } else {
            result.exceptionOrNull()?.let { throwable ->
                //TODO
                //val exception = mapException(throwable)
                //logger.info(throwable)
                val uiError = UIError(throwable = throwable)
                val newState = initialCardState
                    .inDone()
                    .asLoadingPkmSingleCard()
                    .withError(uiError)
                _cardLiveData.postValue(newState)
            }
        }
    }

    fun getExpansionSets() {
        _setsLiveData.value?.let { uiState ->
            val selectedIdName = uiState.result?.selectedIdName ?: return
            val singleCardCollection = uiState.result?.collection ?: return
            if (singleCardCollection.first().idName != selectedIdName) {
                val index = singleCardCollection.indexOfFirst{ it.idName == selectedIdName }
                val collectionPrev = singleCardCollection.subList(0, index)
                val collectionNext = singleCardCollection.subList(index, singleCardCollection.size)
                val orderedCollectionBySelected = mutableListOf<TcgSetInterface>()
                orderedCollectionBySelected.addAll(collectionNext.plus(collectionPrev))
                val newUIState= uiState.copy()
                    .withResult(SingleCardTCGSets(
                        selectedIdName = selectedIdName,
                        collection = orderedCollectionBySelected
                    ))
                postCachedSets(newUIState)
            } else {
                postCachedSets(uiState)
            }
        } ?: run {
            _setsLiveData.postValue(initialSetsState)

            viewModelScope.launch {
                singleCardUseCase.requestSets().collect { result ->
                    handleSetsResponse(result)
                }
            }
        }
    }

    private fun postCachedSets(uiData: UIViewState<SingleCardSetsInterface>) {
        val newState = uiData
            .asLoadingPkmCardSets()
            .inDone()
            .withError(null)
        _setsLiveData.postValue(newState)
    }

    private fun handleSetsResponse(response: Result<List<TcgSetInterface>>) {
        if (response.isSuccess) {
            val content = response.getOrNull()
            content?.let {
                val initialSelectedSet = it.firstOrNull()?.idName
                val newState = initialSetsState
                    .asLoadingPkmCardSets()
                    .inDone()
                    .withResult(SingleCardTCGSets(
                        selectedIdName = initialSelectedSet,
                        collection = it
                    ))
                    .withError(null)
                _setsLiveData.postValue(newState)
            }
        } else {
            response.exceptionOrNull()?.let { throwable ->
                //TODO
                //val exception = mapException(throwable)
                //logger.info(throwable)
                println("logd Error: ${throwable.message}")
                val uiError = UIError(throwable = throwable)
                val newState = initialSetsState
                    .inDone()
                    .withError(uiError)
                _setsLiveData.postValue(newState)
            }
        }
    }

    fun selectSet(index: Int) {
        val currState = _setsLiveData.value ?: return
        val currSingleCardSets = currState.result ?: return
        val selectedSet = currSingleCardSets.collection[index]
        println("logd selectedSet idName: ${selectedSet.idName} of index: $index")
        viewModelScope.launch(Dispatchers.Default) {
            val newState = currState
                .inDone()
                .asPickingPkmCardSet()
                .withResult(
                    SingleCardTCGSets(
                        selectedIdName = selectedSet.idName,
                        currSingleCardSets.collection
                    )
                )
                .withError(null)
            _setsLiveData.postValue(newState)
        }
    }

}
