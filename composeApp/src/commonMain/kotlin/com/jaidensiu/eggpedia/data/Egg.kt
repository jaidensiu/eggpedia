package com.jaidensiu.eggpedia.data

data class Egg(
    val name: String,
    val imageUrl: String,
    val videoUrl: String,
    val cookingSteps: List<String>
)
