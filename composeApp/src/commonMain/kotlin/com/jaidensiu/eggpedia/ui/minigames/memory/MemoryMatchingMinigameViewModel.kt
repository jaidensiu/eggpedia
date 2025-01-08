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
                val randomEggs = eggs.keys.shuffled().take(NUMBER_OF_EGGS)
                val currentEgg = randomEggs.firstOrNull()
                _state.update {
                    it.copy(
                        eggs = eggs,
                        randomEggs = randomEggs,
                        currentEgg = currentEgg
                    )
                }
                shuffleImages()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun onSelectDifficulty(difficulty: MemoryMatchingMinigameDifficulty) {
        _state.update { it.copy(difficulty = difficulty) }
    }

    fun dismissDifficulty() {
        _state.update { it.copy(difficulty = null) }
    }

    fun onPlay() {
        // TODO
    }

    fun getGridItemsSize(): Int {
        return when (_state.value.difficulty) {
            MemoryMatchingMinigameDifficulty.EASY -> EASY_EGGS
            MemoryMatchingMinigameDifficulty.MEDIUM -> MEDIUM_EGGS
            MemoryMatchingMinigameDifficulty.HARD -> HARD_EGGS
            null -> HARD_EGGS
        }
    }

    fun checkImageClicked(imageUrl: String?) {
        val currentEgg = _state.value.currentEgg
        val nextIdx = _state.value.randomEggs.indexOf(currentEgg) + 1
        val score = if (_state.value.eggs[currentEgg] == imageUrl) {
            _state.value.score + 1
        } else {
            _state.value.score
        }
        _state.value = _state.value.copy(
            currentEgg = _state.value.randomEggs[nextIdx],
            errorMessage = null,
            score = score
        )
        hideImages()
        shuffleImages()
        showImages()
    }

    private fun hideImages() {
        // TODO
    }

    private fun showImages() {
        // TODO
    }

    private fun shuffleImages() {
        val currentEgg = _state.value.currentEgg ?: return
        val correctImage = _state.value.eggs[currentEgg]
        val otherImages = _state.value.eggs.values
            .filter { it != correctImage }
            .shuffled()
            .take(n = getGridItemsSize() - 1)
        val shuffledImages = (otherImages + correctImage).shuffled()
        _state.value = _state.value.copy(shuffledImages = shuffledImages)
    }

    companion object {
        const val NUMBER_OF_EGGS = 6
        const val GRID_COLUMNS = 2
        const val EASY_EGGS = 2
        const val MEDIUM_EGGS = 4
        const val HARD_EGGS = 6
    }
}
