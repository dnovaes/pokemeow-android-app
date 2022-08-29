package com.dnovaes.pokemontcg.singleCard.viewmodels

import android.app.appsearch.SearchResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnovaes.commons.data.model.UIError
import com.dnovaes.commons.data.model.UIViewState
import com.dnovaes.commons.data.model.withError
import com.dnovaes.commons.data.model.withResult
import com.dnovaes.pokemontcg.commonFeature.domain.SearchTcgSets
import com.dnovaes.pokemontcg.commonFeature.domain.TcgSetInterface
import com.dnovaes.pokemontcg.singleCard.domain.model.ui.CardInterface
import com.dnovaes.pokemontcg.singleCard.domain.repository.SingleCardUseCaseInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class SingleCardViewModel @Inject constructor(
    private val singleCardUseCase: SingleCardUseCaseInterface,
): ViewModel() {

    private val _cardLiveData: MutableLiveData<UIViewState<CardInterface>> = MutableLiveData()
    val cardLiveData: LiveData<UIViewState<CardInterface>> = _cardLiveData

    private val _setsLiveData: MutableLiveData<UIViewState<SearchTcgSets>> = MutableLiveData()
    val setsLiveData: LiveData<UIViewState<SearchTcgSets>> = _setsLiveData

    fun getCard(id: String) {
        viewModelScope.launch {
            singleCardUseCase.requestCard(id).collect { result ->
                handleSingleCardResponse(result)?.let {
                    _cardLiveData.postValue(it)
                }
            }
        }
    }

    private fun handleSingleCardResponse(
        result: Result<CardInterface>
    ): UIViewState<CardInterface>? =
        if (result.isSuccess) {
            val content = result.getOrNull()
            content?.let {
                UIViewState<CardInterface>()
                    .withResult(it)
                    .withError(null)
            }
        } else {
            result.exceptionOrNull()?.let { throwable ->
                //TODO
                //val exception = mapException(throwable)
                //logger.info(throwable)
                val uiError = UIError(throwable = throwable)
                UIViewState<CardInterface>().withError(uiError)
            }
        }

    fun getExpansionSets() {
        viewModelScope.launch {
            singleCardUseCase.requestSets().collect { result ->
                handleSetsResponse(result)?.let {
                    _setsLiveData.postValue(it)
                }
            }
        }
    }

    private fun handleSetsResponse(response: Result<List<TcgSetInterface>>): UIViewState<SearchTcgSets>? =
        if (response.isSuccess) {
            val content = response.getOrNull()
            content?.let {
                UIViewState<SearchTcgSets>()
                    .withResult(SearchTcgSets(null, it))
                    .withError(null)
            }
        } else {
            response.exceptionOrNull()?.let { throwable ->
                //TODO
                //val exception = mapException(throwable)
                //logger.info(throwable)
                val uiError = UIError(throwable = throwable)
                UIViewState<SearchTcgSets>().withError(uiError)
            }
        }

    fun selectSet(id: String) {
        val prevState = _setsLiveData.value?.result ?: return
        val newState = UIViewState<SearchTcgSets>()
            .withResult(SearchTcgSets(id, prevState.result))
            .withError(null)
        _setsLiveData.postValue(newState)
    }
}
