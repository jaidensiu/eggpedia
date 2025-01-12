package com.jaidensiu.eggpedia.data.local.minigame

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface MinigameDao {
    @Upsert
    suspend fun upsert(minigameEntity: MinigameEntity)

    @Query("SELECT MIN(speedMatchingTime) FROM MinigameEntity WHERE speedMatchingTime IS NOT NULL")
    suspend fun getBestSpeedMatchingTime(): Long?

    @Query("SELECT MIN(memoryMatchingTime) FROM MinigameEntity WHERE memoryMatchingTime IS NOT NULL")
    suspend fun getBestMemoryMatchingTime(): Long?
}
