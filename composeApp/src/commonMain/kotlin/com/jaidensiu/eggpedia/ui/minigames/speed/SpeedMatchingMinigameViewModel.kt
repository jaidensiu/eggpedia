package com.jaidensiu.eggpedia.ui.minigames.speed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaidensiu.eggpedia.data.EggsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

class SpeedMatchingMinigameViewModel(private val repository: EggsRepository) : ViewModel() {
    private val _state = MutableStateFlow(SpeedMatchingMinigameScreenState())
    val state = _state.asStateFlow()

    fun initEggs() {
        viewModelScope.launch {
            try {
                val eggs = repository.getRemoteEggs().associate { it.name to it.imageUrl }
                val randomEggs = eggs.keys.shuffled().take(NUMBER_OF_EGGS)
                _state.update {
                    it.copy(
                        eggs = eggs,
                        randomEggs = randomEggs,
                        currentEgg = randomEggs.firstOrNull()
                    )
                }
                shuffleImages()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun onPlay() {
        _state.update { it.copy(startTime = Clock.System.now()) }
    }

    fun onPlayAgain() {
        _state.value = SpeedMatchingMinigameScreenState()
        initEggs()
        onPlay()
    }

    fun isBestTime(timeMillis: Long?): Boolean {
        if (timeMillis == null) {
            return false
        }
        // TODO
        val prevTimeMillis = 10000L
        return timeMillis < prevTimeMillis
    }

    fun getBestTime(): Long {
        // TODO
        return 0L
    }

    fun checkImageClicked(imageUrl: String?) {
        val currentEgg = _state.value.currentEgg
        if (_state.value.eggs[currentEgg] == imageUrl) {
            val nextIdx = _state.value.randomEggs.indexOf(currentEgg) + 1
            if (nextIdx < _state.value.randomEggs.size) {
                _state.value = _state.value.copy(
                    currentEgg = _state.value.randomEggs[nextIdx],
                    errorMessage = null
                )
                shuffleImages()
            } else {
                val totalTime = Clock.System.now().toEpochMilliseconds().minus(
                    other = _state.value.startTime?.toEpochMilliseconds() ?: 0L
                )
                _state.value = _state.value.copy(totalTime = totalTime)
            }
        } else {
            _state.value = _state.value.copy(errorMessage = "Wrong image, try again!")
        }
    }

    private fun shuffleImages() {
        val currentEgg = _state.value.currentEgg ?: return
        val correctImage = _state.value.eggs[currentEgg]
        val otherImages = _state.value.eggs.values
            .filter { it != correctImage }
            .shuffled()
            .take(n = GRID_ITEMS_SIZE - 1)
        val shuffledImages = (otherImages + correctImage).shuffled()
        _state.value = _state.value.copy(shuffledImages = shuffledImages)
    }

    companion object {
        const val NUMBER_OF_EGGS = 5
        const val GRID_SIZE = 2
        const val GRID_ITEMS_SIZE = 4
    }
}
