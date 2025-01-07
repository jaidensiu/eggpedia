package com.jaidensiu.eggpedia.ui.minigames.memory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaidensiu.eggpedia.data.repositories.egg.EggsRepository
import com.jaidensiu.eggpedia.data.repositories.minigame.MinigamesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MemoryMatchingMinigameViewModel(
    private val eggsRepository: EggsRepository,
    private val minigamesRepository: MinigamesRepository
) : ViewModel() {
    private val _state = MutableStateFlow(MemoryMatchingMinigameScreenState())
    val state = _state.asStateFlow()

    fun initEggs() {
        viewModelScope.launch {
            try {
                val eggs = eggsRepository.getRemoteEggs().associate { it.name to it.imageUrl }
                _state.update {
                    it.copy(eggs = eggs)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun onPlay() {
        // TODO
    }
}
