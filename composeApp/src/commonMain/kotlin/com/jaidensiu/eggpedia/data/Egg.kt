package com.jaidensiu.eggpedia.data

import kotlinx.serialization.Serializable

@Serializable
data class Egg(
    val name: String,
    val image: String,
    val video: String,
    val steps: List<String>
)
