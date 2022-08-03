package com.dnovaes.pokemontcg.singleCard.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnovaes.commons.data.model.UIViewState
import com.dnovaes.commons.data.model.withError
import com.dnovaes.commons.data.model.withResult
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
            pkmTcgRepository.requestCard(id).collect {
                if (it.isSuccess) {
                    var cardState = UIViewState<CardInterface>()
                    val content = it.getOrNull()
                    content?.let {
                        cardState = cardState
                            .withResult(singleCardMapper.mapCard(content))
                            .withError(null)
                        _cardLiveData.postValue(cardState)
                    }
                } else {
                    //_cardLiveData.postValue()
                }
            }
        }
    }

}
