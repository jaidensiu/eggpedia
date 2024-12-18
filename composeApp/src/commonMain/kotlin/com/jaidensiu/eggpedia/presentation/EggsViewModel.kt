package com.jaidensiu.eggpedia.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaidensiu.eggpedia.data.EggsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EggsViewModel(private val repository: EggsRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(EggsUiState())
    val state = _uiState.asStateFlow()

    init {
        getEggs()
    }

    fun getEggs() {
        viewModelScope.launch {
            try {
                val eggs = repository.getEggs()
                _uiState.value = EggsUiState(eggs = eggs)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}