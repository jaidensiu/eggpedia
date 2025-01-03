package com.jaidensiu.eggpedia.ui.games.image

import kotlinx.datetime.Instant

data class ImageMatchingGameScreenState(
    val eggNameToImage: Map<String, String> = emptyMap(),
    val randomEggs: List<String> = emptyList(),
    val currentEgg: String? = null,
    val shuffledImages: List<String?> = emptyList(),
    val errorMessage: String? = null,
    val startTime: Instant? = null,
    val totalTime: Long? = null
)

