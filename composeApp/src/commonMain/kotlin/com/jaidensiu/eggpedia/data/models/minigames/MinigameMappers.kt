package com.jaidensiu.eggpedia.data.models.minigames

import com.jaidensiu.eggpedia.data.local.minigames.MinigameEntity

object MinigameMappers {
    fun Minigame.toMinigameEntity(): MinigameEntity {
        return MinigameEntity(
            recipeMatchingTime = recipeMatchingTime,
            memoryMatchingTime = memoryMatchingTime
        )
    }
}
