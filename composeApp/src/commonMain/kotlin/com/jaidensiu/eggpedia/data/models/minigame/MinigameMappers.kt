package com.jaidensiu.eggpedia.data.models.minigame

import com.jaidensiu.eggpedia.data.local.minigame.MinigameEntity

object MinigameMappers {
    fun MinigameEntity.toMinigame(): Minigame {
        return Minigame(
            speedMatchingTime = speedMatchingTime,
            memoryMatchingTime = memoryMatchingTime
        )
    }

    fun Minigame.toMinigameEntity(): MinigameEntity {
        return MinigameEntity(
            speedMatchingTime = speedMatchingTime,
            memoryMatchingTime = memoryMatchingTime
        )
    }
}
