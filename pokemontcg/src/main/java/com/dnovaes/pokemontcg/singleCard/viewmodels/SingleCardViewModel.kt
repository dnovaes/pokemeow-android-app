package com.dnovaes.pokemontcg.singleCard.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnovaes.pokemontcg.singleCard.repository.PokemonTcgRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SingleCardViewModel: ViewModel() {

    private val pkmTcgRepository: PokemonTcgRepository = PokemonTcgRepository()

    fun getCard() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = pkmTcgRepository.getCard()
            println("logd result: $result")
        }
    }

}