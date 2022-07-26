package com.dnovaes.pokemontcg.singleCard.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnovaes.pokemontcg.singleCard.domain.model.TcgCardServerResponse
import com.dnovaes.pokemontcg.singleCard.domain.repository.TcgRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class SingleCardViewModel @Inject constructor(
    private val pkmTcgRepository: TcgRepositoryInterface
): ViewModel() {

    private val _cardLiveData: MutableLiveData<TcgCardServerResponse> = MutableLiveData()
    val cardLiveData: LiveData<TcgCardServerResponse> = _cardLiveData

    fun getCard(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            pkmTcgRepository.requestCard(id).collect {
                _cardLiveData.postValue(it)
            }
        }
    }

}