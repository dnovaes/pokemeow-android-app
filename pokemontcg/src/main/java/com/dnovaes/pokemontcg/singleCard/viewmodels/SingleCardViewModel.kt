package com.dnovaes.pokemontcg.singleCard.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnovaes.pokemontcg.singleCard.domain.repository.TcgRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class SingleCardViewModel @Inject constructor(
    private val pkmTcgRepository: TcgRepositoryInterface
): ViewModel() {

    fun getCard(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = pkmTcgRepository.requestCard(id)
            println("logd result in singleCardViewModel: $result")
        }
    }

}