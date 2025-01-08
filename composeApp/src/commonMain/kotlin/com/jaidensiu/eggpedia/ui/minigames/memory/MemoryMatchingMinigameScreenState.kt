package com.jaidensiu.eggpedia.ui.minigames.memory

data class MemoryMatchingMinigameScreenState(
    val eggs: Map<String, String> = emptyMap(),
    val randomEggs: List<String> = emptyList(),
    val currentEgg: String? = null,
    val difficulty: MemoryMatchingMinigameDifficulty? = null,
    val shuffledImages: List<String?> = emptyList(),
    val errorMessage: String? = null,
    val score: Int = 0
)
