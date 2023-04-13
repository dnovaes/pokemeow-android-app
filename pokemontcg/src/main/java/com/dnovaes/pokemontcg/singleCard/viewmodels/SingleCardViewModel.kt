package com.dnovaes.pokemontcg.singleCard.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnovaes.commons.data.model.UIError
import com.dnovaes.commons.data.model.uiviewstate.UIDataState
import com.dnovaes.commons.data.model.uiviewstate.UIViewState
import com.dnovaes.pokemontcg.R
import com.dnovaes.pokemontcg.commonFeature.domain.TcgSetInterface
import com.dnovaes.pokemontcg.singleCard.domain.model.ui.SingleCardTCGSets
import com.dnovaes.pokemontcg.singleCard.data.model.SingleCardUIDataProcess
import com.dnovaes.pokemontcg.singleCard.data.model.asDoneLoadingPkmSingleCard
import com.dnovaes.pokemontcg.singleCard.data.model.asDoneLoadingPkmCardSet
import com.dnovaes.pokemontcg.singleCard.data.model.asDoneSelectingPkmCardSet
import com.dnovaes.pokemontcg.singleCard.data.model.asProcessingLoadingPkmSingleCard
import com.dnovaes.pokemontcg.singleCard.domain.model.ui.Card
import com.dnovaes.pokemontcg.singleCard.domain.monitoring.SingleCardMonitoring
import com.dnovaes.pokemontcg.singleCard.domain.repository.SingleCardUseCaseInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class SingleCardViewModel @Inject constructor(
    private val monitoring: SingleCardMonitoring,
    private val singleCardUseCase: SingleCardUseCaseInterface,
): ViewModel() {

    private val _cardLiveData: MutableLiveData<UIViewState<Card>> = MutableLiveData()
    val cardLiveData: LiveData<UIViewState<Card>> = _cardLiveData
    private var currCardState = UIViewState<Card>(
        process = SingleCardUIDataProcess.LOADING_SINGLE_CARD,
        state = UIDataState.PROCESSING
    )

    private val _setsLiveData: MutableLiveData<UIViewState<SingleCardTCGSets>> = MutableLiveData()
    val setsLiveData: LiveData<UIViewState<SingleCardTCGSets>> = _setsLiveData
    private var currCardSetsState = UIViewState<SingleCardTCGSets>(
        process = SingleCardUIDataProcess.LOADING_CARD_SETS,
        state = UIDataState.PROCESSING
    )

    fun getCard(cardNumber: String) {
        val sets = currCardSetsState.result ?: return
        if (cardNumber.isBlank()) return
        val selectedExpansionSet = sets.selectedIdName ?: sets.collection.firstOrNull()?.idName ?: run {
            postUnselectedSetError()
            return
        }
        currCardState = currCardState
            .asProcessingLoadingPkmSingleCard()
            .withResult(currCardState.result?.copy(lastCardNumberSearched = cardNumber))
            .withError(null)
        _cardLiveData.postValue(currCardState)
        viewModelScope.launch {
            val cardSetId = "$selectedExpansionSet-${cardNumber}"
            singleCardUseCase.requestCard(cardSetId).collect { result ->
                handleSingleCardResponse(result as Result<Card>, selectedExpansionSet, cardNumber)
            }
        }
    }

    private fun postUnselectedSetError() {
        currCardState = currCardState
            .asDoneLoadingPkmSingleCard()
            .withResult(null)
            .withError(UIError(R.string.search_failure_set_not_selected))
        _cardLiveData.postValue(currCardState)
    }

    private fun handleSingleCardResponse(
        result: Result<Card>,
        selectedExpansionIdName: String,
        cardNumberSearched: String
    ) {
        val logParams = mapOf("cardNumber" to cardNumberSearched, "expansionId" to selectedExpansionIdName)
        val action = "getCard"
        if (result.isSuccess) {
            val content = result.getOrNull()
            monitoring.logCardSuccessResponse(action, logParams)
            content?.let {
                currCardState = currCardState
                    .asDoneLoadingPkmSingleCard()
                    .withResult(it.copy(lastCardNumberSearched = cardNumberSearched))
                    .withError(null)
                _cardLiveData.postValue(currCardState)
            }
        } else {
            monitoring.logCardFailureResponse(action, logParams, result.exceptionOrNull())
            result.exceptionOrNull()?.let { throwable ->
                //TODO
                //val exception = mapException(throwable)
                //logger.info(throwable)
                val uiError = UIError(throwable = throwable)
                currCardState = currCardState
                    .asDoneLoadingPkmSingleCard()
                    .withError(uiError)
                _cardLiveData.postValue(currCardState)
            }
        }
    }

    fun getExpansionSets() {
        currCardSetsState.result?.let { uiState ->
            val selectedIdName = uiState.selectedIdName ?: return
            val singleCardCollection = uiState.collection
            if (singleCardCollection.first().idName != selectedIdName) {
                val index = singleCardCollection.indexOfFirst{ it.idName == selectedIdName }
                val collectionPrev = singleCardCollection.subList(0, index)
                val collectionNext = singleCardCollection.subList(index, singleCardCollection.size)
                val orderedCollectionBySelected = mutableListOf<TcgSetInterface>()
                orderedCollectionBySelected.addAll(collectionNext.plus(collectionPrev))
                currCardSetsState = currCardSetsState
                    .asDoneLoadingPkmCardSet()
                    .withResult(SingleCardTCGSets(
                        selectedIdName = selectedIdName,
                        collection = orderedCollectionBySelected
                    ))
            }
            postCachedSets(currCardSetsState)
        } ?: run {
            _setsLiveData.postValue(currCardSetsState)

            viewModelScope.launch {
                singleCardUseCase.requestSets().collect { result ->
                    handleSetsResponse(result)
                }
            }
        }
    }

    private fun postCachedSets(uiData: UIViewState<SingleCardTCGSets>) {
        currCardSetsState = uiData
            .asDoneLoadingPkmCardSet()
            .withError(null)
        _setsLiveData.postValue(currCardSetsState)
    }

    private fun handleSetsResponse(response: Result<List<TcgSetInterface>>) {
        if (response.isSuccess) {
            val content = response.getOrNull()
            content?.let {
                val initialSelectedSet = it.firstOrNull()?.idName
                currCardSetsState = currCardSetsState
                    .asDoneLoadingPkmCardSet()
                    .withResult(SingleCardTCGSets(
                        selectedIdName = initialSelectedSet,
                        collection = it
                    ))
                    .withError(null)
                _setsLiveData.postValue(currCardSetsState)
            }
        } else {
            response.exceptionOrNull()?.let { throwable ->
                //TODO
                //val exception = mapException(throwable)
                //logger.info(throwable)
                println("logd Error: ${throwable.message}")
                val uiError = UIError(throwable = throwable)
                currCardSetsState = currCardSetsState
                    .asDoneLoadingPkmCardSet()
                    .withError(uiError)
                _setsLiveData.postValue(currCardSetsState)
            }
        }
    }

    fun selectSet(index: Int) {
        val currSingleCardSets = currCardSetsState.result ?: return
        val selectedSet = currSingleCardSets.collection[index]
        println("logd selectedSet idName: ${selectedSet.idName} of index: $index")
        viewModelScope.launch(Dispatchers.Default) {
            currCardSetsState = currCardSetsState
                .asDoneSelectingPkmCardSet()
                .withResult(
                    currSingleCardSets.copy(
                        selectedIdName = selectedSet.idName,
                        collection = currSingleCardSets.collection
                    )
                )
                .withError(null)
            _setsLiveData.postValue(currCardSetsState)
        }
    }

    fun loadPrevCardInCurrCollection() {
        val lastCardNumber = currCardState.result?.lastCardNumberSearched ?: return
        if (lastCardNumber.isBlank()) return
        val nextCard = lastCardNumber.toInt(10) - 1
        getCard(nextCard.toString())
    }

    fun loadNextCardInCurrCollection() {
        val lastCardNumber = currCardState.result?.lastCardNumberSearched ?: return
        if (lastCardNumber.isBlank()) return
        val nextCardNumber = lastCardNumber.toInt(10) + 1
        getCard(nextCardNumber.toString())
    }


}
