package com.jaidensiu.eggpedia.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaidensiu.eggpedia.app.Route
import com.jaidensiu.eggpedia.data.EggsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EggsListViewModel(private val repository: EggsRepository) : ViewModel() {
    private val _state = MutableStateFlow(EggsListScreenState())
    val state = _state.asStateFlow()

    fun getEggs(route: Route) {
        viewModelScope.launch {
            try {
                if (route == Route.EggsList) {
                    val eggs = repository.getEggs()
                    _state.value = EggsListScreenState(eggs = eggs)
                } else if (route == Route.SavedEggsList) {
                    // TODO: get cached eggs from db
                    val eggs = repository.getEggs()
                    _state.value = EggsListScreenState(eggs = eggs)
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
