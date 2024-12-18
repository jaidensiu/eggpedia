package com.jaidensiu.eggpedia.presentation

import com.jaidensiu.eggpedia.data.Egg

data class EggsUiState(
    val eggs: List<Egg> = emptyList()
)
