package com.jaidensiu.eggpedia.ui.game

import com.jaidensiu.eggpedia.data.Egg

data class EggQuizGameScreenState(
    val eggs: List<Egg> = emptyList(),
    val isConnectedToInternet: Boolean = false
)
