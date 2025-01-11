package com.jaidensiu.eggpedia.ui.minigames.memory

import kotlinx.datetime.Instant

data class MemoryMatchingMinigameScreenState(
    val eggImages: List<String> = emptyList(),
    val randomEggImages: List<String> = emptyList(),
    val flippedCards: List<Int> = emptyList(),
    val matchedCards: List<Int> = emptyList(),
    val difficulty: MemoryMatchingMinigameDifficulty? = null,
    val errorMessage: String? = null,
    val startTime: Instant? = null,
    val totalTime: Long? = null
)
