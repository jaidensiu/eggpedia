package com.jaidensiu.eggpedia.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaidensiu.eggpedia.app.Route
import com.jaidensiu.eggpedia.data.EggsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EggsListViewModel(private val repository: EggsRepository) : ViewModel() {
    private val _state = MutableStateFlow(EggsListScreenState())
    val state = _state.asStateFlow()

    fun getEggs(route: Route) {
        viewModelScope.launch {
            try {
                if (route == Route.EggsList) {
                    val eggs = repository.getRemoteEggs()
                    _state.value = EggsListScreenState(eggs = eggs)
                } else if (route == Route.SavedEggsList) {
                    repository.getLocalEggs()
                        .onEach { eggs -> _state.value = EggsListScreenState(eggs = eggs) }
                        .collect()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _state.update { it.copy(searchQuery = query) }
    }
}
