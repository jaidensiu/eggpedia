package com.jaidensiu.eggpedia.presentation.list

import com.jaidensiu.eggpedia.data.Egg

data class EggsListScreenState(
    val eggs: List<Egg> = emptyList()
)
