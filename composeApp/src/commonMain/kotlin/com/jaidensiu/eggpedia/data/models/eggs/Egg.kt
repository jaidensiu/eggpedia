package com.jaidensiu.eggpedia.data.models.eggs

import kotlinx.serialization.Serializable

@Serializable
data class Egg(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val videoUrl: String,
    val cookingSteps: List<String>
)
