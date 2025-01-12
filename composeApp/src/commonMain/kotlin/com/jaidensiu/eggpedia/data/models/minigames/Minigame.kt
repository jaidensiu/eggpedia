package com.jaidensiu.eggpedia.data.models.minigames

import kotlinx.serialization.Serializable

@Serializable
data class Minigame(
    val recipeMatchingTime: Long?,
    val memoryMatchingTime: Long?
)
