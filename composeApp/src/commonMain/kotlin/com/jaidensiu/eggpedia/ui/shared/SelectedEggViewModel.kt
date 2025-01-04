package com.jaidensiu.eggpedia.ui.shared

import androidx.lifecycle.ViewModel
import com.jaidensiu.eggpedia.data.models.egg.Egg
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SelectedEggViewModel : ViewModel() {
    private val _selectedEgg = MutableStateFlow<Egg?>(value = null)
    val selectedEgg = _selectedEgg.asStateFlow()

    fun onSelectEgg(egg: Egg?) {
        _selectedEgg.value = egg
    }
}
