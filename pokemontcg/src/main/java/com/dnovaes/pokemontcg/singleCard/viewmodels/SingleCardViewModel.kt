package com.dnovaes.pokemontcg.singleCard.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnovaes.commons.data.model.UIError
import com.dnovaes.commons.data.model.UIViewState
import com.dnovaes.commons.data.model.withError
import com.dnovaes.commons.data.model.withResult
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

/*
    private val _setsLiveData: MutableLiveData<UIViewState<SetsInterface>> = MutableLiveData()
    val setsLiveData: LiveData<UIViewState<SetInterface>> = _setsLiveData
*/

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
                println("logd $result")
            }
        }
    }
}
