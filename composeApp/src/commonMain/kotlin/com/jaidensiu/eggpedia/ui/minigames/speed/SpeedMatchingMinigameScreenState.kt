package com.jaidensiu.eggpedia.ui.minigames.speed

import kotlinx.datetime.Instant

data class SpeedMatchingMinigameScreenState(
    val eggs: Map<String, String> = emptyMap(),
    val randomEggs: List<String> = emptyList(),
    val currentEgg: String? = null,
    val shuffledImages: List<String?> = emptyList(),
    val errorMessage: String? = null,
    val startTime: Instant? = null,
    val totalTime: Long? = null
)
