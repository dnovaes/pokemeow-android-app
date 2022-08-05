package com.dnovaes.pokemontcg.singleCard.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnovaes.commons.data.model.UIError
import com.dnovaes.commons.data.model.UIViewState
import com.dnovaes.commons.data.model.withError
import com.dnovaes.commons.data.model.withResult
import com.dnovaes.pokemontcg.singleCard.domain.model.CardResponseInterface
import com.dnovaes.pokemontcg.singleCard.domain.model.ui.CardInterface
import com.dnovaes.pokemontcg.singleCard.domain.repository.TcgRepositoryInterface
import com.dnovaes.pokemontcg.singleCard.domain.repository.mapper.SingleCardMapperInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class SingleCardViewModel @Inject constructor(
    private val pkmTcgRepository: TcgRepositoryInterface,
    private val singleCardMapper: SingleCardMapperInterface
): ViewModel() {

    private val _cardLiveData: MutableLiveData<UIViewState<CardInterface>> = MutableLiveData()
    val cardLiveData: LiveData<UIViewState<CardInterface>> = _cardLiveData

    fun getCard(id: String) {
        viewModelScope.launch {
            pkmTcgRepository.requestCard(id).collect { result ->
                handleSingleCardResponse(result)?.let {
                    _cardLiveData.postValue(it)
                }
            }
        }
    }

    private fun handleSingleCardResponse(
        result: Result<CardResponseInterface>
    ): UIViewState<CardInterface>? =
        if (result.isSuccess) {
            val content = result.getOrNull()
            content?.let {
                UIViewState<CardInterface>()
                    .withResult(singleCardMapper.mapCard(content))
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
}
