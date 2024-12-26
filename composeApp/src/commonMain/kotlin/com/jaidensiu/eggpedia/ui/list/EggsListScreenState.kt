package com.jaidensiu.eggpedia.ui.list

import com.jaidensiu.eggpedia.data.Egg

data class EggsListScreenState(
    val eggs: List<Egg> = emptyList(),
    val searchQuery: String = ""
)
