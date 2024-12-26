package com.jaidensiu.eggpedia.data

import kotlinx.serialization.Serializable

@Serializable
data class Egg(
    val name: String,
    val imageUrl: String,
    val videoUrl: String,
    val cookingSteps: List<String>
)
