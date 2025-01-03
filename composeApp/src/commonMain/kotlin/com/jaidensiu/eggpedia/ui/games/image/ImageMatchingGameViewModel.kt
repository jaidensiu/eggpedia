package com.jaidensiu.eggpedia.ui.games.image

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaidensiu.eggpedia.data.EggsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

class ImageMatchingGameViewModel(private val repository: EggsRepository) : ViewModel() {
    private val _state = MutableStateFlow(ImageMatchingGameScreenState())
    val state = _state.asStateFlow()

    fun initEggNamesAndImages() {
        viewModelScope.launch {
            try {
                val eggs = repository.getRemoteEggs()
                val eggNameToImage = eggs.associate { it.name to it.imageUrl }
                val randomEggs = eggNameToImage.keys.shuffled().take(NUMBER_OF_EGGS)
                _state.value = ImageMatchingGameScreenState(
                    eggNameToImage = eggNameToImage,
                    randomEggs = randomEggs,
                    currentEgg = randomEggs.firstOrNull(),
                    startTime = Clock.System.now()
                )
                shuffleImages()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun checkImageClicked(imageUrl: String?) {
        val currentEgg = _state.value.currentEgg
        if (_state.value.eggNameToImage[currentEgg] == imageUrl) {
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
                _state.value = _state.value.copy(
                    currentEgg = null,
                    totalTime = totalTime
                )
            }
        } else {
            _state.value = _state.value.copy(errorMessage = "Wrong image, try again!")
        }
    }

    private fun shuffleImages() {
        val currentEgg = _state.value.currentEgg ?: return
        val correctImage = _state.value.eggNameToImage[currentEgg]
        val otherImages = _state.value.eggNameToImage.values
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
