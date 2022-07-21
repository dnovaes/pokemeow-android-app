package com.dnovaes.pokemeow.inventory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnovaes.pokemeow.inventory.repository.InventoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class InventoryViewModel @Inject constructor(
    private val repository: InventoryRepository
): ViewModel() {

    fun getInventory() {
        viewModelScope.launch(Dispatchers.IO){
            println("logd InvViewModel) calling getInventory")
            repository.getInventory()
        }
    }
}
