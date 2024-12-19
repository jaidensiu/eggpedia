package com.jaidensiu.eggpedia.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EggDto(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("image_url") val imageUrl: String,
    @SerialName("video_url") val videoUrl: String,
    @SerialName("cooking_steps") val cookingSteps: List<String>
)
