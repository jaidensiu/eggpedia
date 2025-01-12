package com.jaidensiu.eggpedia.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaidensiu.eggpedia.data.models.eggs.Egg
import com.jaidensiu.eggpedia.data.repositories.eggs.EggsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EggDetailsViewModel(private val repository: EggsRepository) : ViewModel() {
    private val _state = MutableStateFlow(value = EggDetailsScreenState())
    val state = _state.asStateFlow()

    fun saveEggToLocal(egg: Egg) {
        viewModelScope.launch {
            try {
                repository.saveEggToLocal(egg)
                _state.update { it.copy(isSaved = true) }
            } catch (e: Exception) {
                _state.update { it.copy(isSaved = false) }
            }
        }
    }

    fun deleteEggFromLocal(id: String) {
        viewModelScope.launch {
            try {
                repository.removeEggFromLocal(id)
                _state.update { it.copy(isSaved = false) }
            } catch (e: Exception) {
                _state.update { it.copy(isSaved = true) }
            }
        }
    }

    fun observeSavedStatus(id: String) {
        repository.isEggSaved(id)
            .onEach { isSaved -> _state.update { it.copy(isSaved = isSaved) } }
            .launchIn(viewModelScope)
    }
}
