package com.jaidensiu.eggpedia.ui.minigames.speed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaidensiu.eggpedia.app.Route
import com.jaidensiu.eggpedia.data.repositories.egg.EggsRepository
import com.jaidensiu.eggpedia.data.repositories.minigame.MinigamesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

class SpeedMatchingMinigameViewModel(
    private val eggsRepository: EggsRepository,
    private val minigamesRepository: MinigamesRepository
) : ViewModel() {
    private val _state = MutableStateFlow(SpeedMatchingMinigameScreenState())
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

    fun onPlay() {
        _state.update { it.copy(startTime = Clock.System.now()) }
    }

    fun onResetMinigameState() {
        _state.value = SpeedMatchingMinigameScreenState()
    }

    suspend fun saveTime(time: Long?) {
        time?.let {
            minigamesRepository.saveMinigameBestTime(
                time = it,
                route = Route.SpeedMatchingMinigame
            )
        }
    }

    suspend fun getBestTime(): Long? {
        return minigamesRepository.getSpeedMatchingBestTime()
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
            .take(n = TOTAL_GRID_ITEMS - 1)
        val shuffledImages = (otherImages + correctImage).shuffled()
        _state.value = _state.value.copy(shuffledImages = shuffledImages)
    }

    companion object {
        const val NUMBER_OF_EGGS = 5
        const val GRID_COLUMNS = 2
        const val TOTAL_GRID_ITEMS = 4
    }
}
