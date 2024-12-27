package com.jaidensiu.eggpedia.ui.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaidensiu.eggpedia.data.EggsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EggQuizGameViewModel(private val repository: EggsRepository) : ViewModel() {
    private val _state = MutableStateFlow(value = EggQuizGameScreenState())
    val state = _state.asStateFlow()

    fun observeInternetConnection(isConnected: Boolean) {
        _state.update { it.copy(isConnectedToInternet = isConnected) }
    }

    fun getEggs() {
        viewModelScope.launch {
            try {
                val eggs = repository.getRemoteEggs()
                _state.update { it.copy(eggs = eggs) }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
