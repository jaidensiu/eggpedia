package com.jaidensiu.eggpedia.ui.games.image

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaidensiu.eggpedia.data.EggsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ImageMatchingGameViewModel(private val repository: EggsRepository) : ViewModel() {
    private val _state = MutableStateFlow(ImageMatchingGameScreenState())
    val state = _state.asStateFlow()

    fun initEggNamesAndImages() {
        viewModelScope.launch {
            try {
                val eggs = repository.getRemoteEggs()
                val eggNameToImage = mutableMapOf<String, String>()
                eggs.forEach { eggNameToImage[it.name] = it.imageUrl }
                _state.value = ImageMatchingGameScreenState(
                    eggNames = eggNameToImage.keys.toList(),
                    eggImages = eggNameToImage.values.toList()
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
