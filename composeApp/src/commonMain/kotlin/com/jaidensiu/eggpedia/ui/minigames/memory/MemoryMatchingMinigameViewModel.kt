package com.jaidensiu.eggpedia.ui.minigames.memory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaidensiu.eggpedia.data.repositories.egg.EggsRepository
import com.jaidensiu.eggpedia.data.repositories.minigame.MinigamesRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

class MemoryMatchingMinigameViewModel(
    private val eggsRepository: EggsRepository,
    private val minigamesRepository: MinigamesRepository
) : ViewModel() {
    private val _state = MutableStateFlow(MemoryMatchingMinigameScreenState())
    val state = _state.asStateFlow()

    fun initEggs() {
        viewModelScope.launch {
            try {
                val eggImages = eggsRepository.getRemoteEggs()
                    .shuffled()
                    .take(NUMBER_OF_EGGS)
                    .map { it.imageUrl }
                _state.update { it.copy(eggImages = eggImages) }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun onSelectDifficulty(difficulty: MemoryMatchingMinigameDifficulty) {
        _state.update { it.copy(difficulty = difficulty) }
    }

    fun dismissDifficulty() {
        _state.update {
            it.copy(
                difficulty = null,
                flippedCards = emptyList(),
                matchedCards = emptyList(),
                randomEggImages = emptyList()
            )
        }
    }

    fun onPlay() {
        val selectedEggImages = _state.value.eggImages.take(n = getGridItemsSize() / 2)
        val randomEggImages = selectedEggImages.flatMap { listOf(it, it) }.shuffled()

        _state.update {
            it.copy(
                randomEggImages = randomEggImages,
                flippedCards = emptyList(),
                matchedCards = emptyList(),
                startTime = Clock.System.now()
            )
        }
    }

    fun getGridItemsSize(): Int {
        return when (_state.value.difficulty) {
            MemoryMatchingMinigameDifficulty.EASY -> EASY_EGGS
            MemoryMatchingMinigameDifficulty.MEDIUM -> MEDIUM_EGGS
            MemoryMatchingMinigameDifficulty.HARD -> HARD_EGGS
            null -> HARD_EGGS
        }.let { it * 2 }
    }

    fun checkImageClicked(idx: Int) {
        val flippedCards = _state.value.flippedCards.toMutableList()
        val matchedCards = _state.value.matchedCards.toMutableList()

        if (flippedCards.size == 2 || flippedCards.contains(idx) || matchedCards.contains(idx)) {
            return
        }

        flippedCards.add(idx)
        _state.update { it.copy(flippedCards = flippedCards) }

        if (flippedCards.size == 2) {
            viewModelScope.launch {
                if (_state.value.randomEggImages[flippedCards[0]] == _state.value.randomEggImages[flippedCards[1]]) {
                    matchedCards.addAll(flippedCards)
                } else {
                    delay(timeMillis = 500L)
                }

                _state.update { it.copy(flippedCards = emptyList(), matchedCards = matchedCards) }
            }
        }
    }

    companion object {
        const val NUMBER_OF_EGGS = 4
        const val GRID_COLUMNS = 2
        const val EASY_EGGS = 2
        const val MEDIUM_EGGS = 3
        const val HARD_EGGS = 4
    }
}
