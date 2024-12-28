package com.jaidensiu.eggpedia.ui.games

import com.jaidensiu.eggpedia.data.Egg

data class EggQuizGamesScreenState(
    val eggs: List<Egg> = emptyList(), // TODO: could probably remove this?
    val isConnectedToInternet: Boolean = false
)
