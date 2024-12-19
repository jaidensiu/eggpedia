package com.jaidensiu.eggpedia.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaidensiu.eggpedia.data.EggsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EggsListViewModel(private val repository: EggsRepository) : ViewModel() {
    private val _state = MutableStateFlow(EggsListScreenState())
    val state = _state.asStateFlow()

    init {
        getEggs()
    }

    fun getEggs() {
        viewModelScope.launch {
            try {
                val eggs = repository.getEggs()
                _state.value = EggsListScreenState(eggs = eggs)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
