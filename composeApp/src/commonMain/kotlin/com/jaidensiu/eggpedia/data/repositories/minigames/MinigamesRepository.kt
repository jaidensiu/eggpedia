package com.jaidensiu.eggpedia.data.repositories.minigames

import androidx.sqlite.SQLiteException
import com.jaidensiu.eggpedia.app.Route
import com.jaidensiu.eggpedia.data.local.minigames.MinigamesDao
import com.jaidensiu.eggpedia.data.models.minigames.Minigame
import com.jaidensiu.eggpedia.data.models.minigames.MinigameMappers.toMinigameEntity

class MinigamesRepository(private val minigamesDao: MinigamesDao) : IMinigamesRepository {
    override suspend fun saveMinigameBestTime(time: Long, route: Route) {
        try {
            val minigame = when (route) {
                Route.RecipeMatchingMinigame -> {
                    Minigame(
                        recipeMatchingTime = time,
                        memoryMatchingTime = null
                    )
                }

                Route.MemoryMatchingMinigame -> {
                    Minigame(
                        recipeMatchingTime = null,
                        memoryMatchingTime = time
                    )
                }

                else -> {
                    Minigame(
                        recipeMatchingTime = null,
                        memoryMatchingTime = null
                    )
                }
            }
            minigamesDao.upsert(minigameEntity = minigame.toMinigameEntity())
        } catch (e: SQLiteException) {
            e.printStackTrace()
        }
    }

    override suspend fun getRecipeMatchingBestTime(): Long? {
        return minigamesDao.getBestRecipeMatchingTime()
    }

    override suspend fun getMemoryMatchingBestTime(): Long? {
        return minigamesDao.getBestMemoryMatchingTime()
    }
}
