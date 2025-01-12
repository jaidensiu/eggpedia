package com.jaidensiu.eggpedia.ui.details

import com.jaidensiu.eggpedia.data.models.eggs.Egg

data class EggDetailsScreenState(
    val egg: Egg? = null,
    val isSaved: Boolean = false
)
