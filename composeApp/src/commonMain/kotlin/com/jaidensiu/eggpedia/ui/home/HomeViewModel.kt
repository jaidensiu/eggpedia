package com.jaidensiu.eggpedia.ui.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel : ViewModel() {
    private val _state = MutableStateFlow(value = HomeScreenState())
    val state = _state.asStateFlow()

    init {
        updateTimeOfDay()
        registerTimeChangeListener { updateTimeOfDay() }
    }

    private fun updateTimeOfDay() {
        val timeOfDay = getTimeOfDay()
        _state.update { it.copy(timeOfDay = timeOfDay) }
    }

    override fun onCleared() {
        super.onCleared()
        unregisterTimeChangeListener()
    }
}
