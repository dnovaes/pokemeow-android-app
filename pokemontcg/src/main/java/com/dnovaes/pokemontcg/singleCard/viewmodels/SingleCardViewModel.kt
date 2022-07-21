package com.dnovaes.pokemontcg.singleCard.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnovaes.pokemontcg.singleCard.repository.PokemonTcgRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SingleCardViewModel(
    private val pkmTcgRepository: PokemonTcgRepository
): ViewModel() {

    fun getCard(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = pkmTcgRepository.requestCard(id)
            println("logd result: $result")
        }
    }

}