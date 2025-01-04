package com.jaidensiu.eggpedia.data.repositories.minigame

import androidx.sqlite.SQLiteException
import com.jaidensiu.eggpedia.app.Route
import com.jaidensiu.eggpedia.data.local.minigame.MinigameDao
import com.jaidensiu.eggpedia.data.models.minigame.Minigame
import com.jaidensiu.eggpedia.data.models.minigame.MinigameMappers.toMinigameEntity

class MinigamesRepository(private val minigameDao: MinigameDao) : IMinigamesRepository {
    override suspend fun saveMinigameBestTime(time: Long, route: Route) {
        try {
            val minigame = when (route) {
                Route.SpeedMatchingMinigame -> {
                    Minigame(
                        speedMatchingTime = time,
                        memoryMatchingTime = null
                    )
                }

                Route.MemoryMatchingMinigame -> {
                    Minigame(
                        speedMatchingTime = null,
                        memoryMatchingTime = time
                    )
                }

                else -> {
                    Minigame(
                        speedMatchingTime = null,
                        memoryMatchingTime = null
                    )
                }
            }
            minigameDao.upsert(minigameEntity = minigame.toMinigameEntity())
        } catch (e: SQLiteException) {
            e.printStackTrace()
        }
    }

    override suspend fun getSpeedMatchingBestTime(): Long? {
        return minigameDao.getBestSpeedMatchingTime()
    }

    override suspend fun getMemoryMatchingBestTime(): Long? {
        return minigameDao.getBestMemoryMatchingTime()
    }
}
