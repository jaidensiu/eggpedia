package com.jaidensiu.eggpedia.ui.minigames.memory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaidensiu.eggpedia.app.Route
import com.jaidensiu.eggpedia.data.repositories.eggs.EggsRepository
import com.jaidensiu.eggpedia.data.repositories.minigames.MinigamesRepository
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
                val randomEggImages = eggImages
                    .take(TOTAL_CARDS)
                    .flatMap { listOf(it, it) }
                    .shuffled()
                _state.update {
                    it.copy(
                        eggImages = eggImages,
                        randomEggImages = randomEggImages
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun onPlay() {
        _state.update { it.copy(startTime = Clock.System.now()) }
    }

    fun onResetMinigameState() {
        _state.value = MemoryMatchingMinigameScreenState()
    }

    suspend fun saveTime(time: Long?) {
        time?.let {
            minigamesRepository.saveMinigameBestTime(
                time = it,
                route = Route.MemoryMatchingMinigame
            )
        }
    }

    suspend fun getBestTime(): Long? {
        return minigamesRepository.getMemoryMatchingBestTime()
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

        if (matchedCards.size == TOTAL_CARDS) {
            val totalTime = Clock.System.now().toEpochMilliseconds().minus(
                other = _state.value.startTime?.toEpochMilliseconds() ?: 0L
            )
            _state.value = _state.value.copy(totalTime = totalTime)
        }
    }

    companion object {
        const val NUMBER_OF_EGGS = 4
        const val GRID_COLUMNS = 2
        const val TOTAL_CARDS = 8
    }
}
