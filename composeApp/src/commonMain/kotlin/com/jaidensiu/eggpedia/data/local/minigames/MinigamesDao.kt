package com.jaidensiu.eggpedia.data.local.minigames

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface MinigamesDao {
    @Upsert
    suspend fun upsert(minigameEntity: MinigameEntity)

    @Query("SELECT MIN(recipeMatchingTime) FROM MinigameEntity WHERE recipeMatchingTime IS NOT NULL")
    suspend fun getBestRecipeMatchingTime(): Long?

    @Query("SELECT MIN(memoryMatchingTime) FROM MinigameEntity WHERE memoryMatchingTime IS NOT NULL")
    suspend fun getBestMemoryMatchingTime(): Long?
}
