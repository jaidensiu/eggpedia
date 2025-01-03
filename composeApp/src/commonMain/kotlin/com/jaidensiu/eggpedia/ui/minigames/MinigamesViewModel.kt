package com.jaidensiu.eggpedia.ui.minigames

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MinigamesViewModel : ViewModel() {
    private val _state = MutableStateFlow(value = MinigamesScreenState())
    val state = _state.asStateFlow()

    fun observeInternetConnection(isConnected: Boolean) {
        _state.update { it.copy(isConnectedToInternet = isConnected) }
    }
}
