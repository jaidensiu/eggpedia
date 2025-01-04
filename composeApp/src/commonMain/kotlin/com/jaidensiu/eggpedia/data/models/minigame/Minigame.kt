package com.jaidensiu.eggpedia.data.models.minigame

import kotlinx.serialization.Serializable

@Serializable
data class Minigame(
    val speedMatchingTime: Long?,
    val memoryMatchingTime: Long?
)
