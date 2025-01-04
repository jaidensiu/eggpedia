package com.jaidensiu.eggpedia.data.repositories.minigame

import com.jaidensiu.eggpedia.app.Route

interface IMinigamesRepository {
    suspend fun saveMinigameBestTime(time: Long, route: Route)
    suspend fun getSpeedMatchingBestTime(): Long?
    suspend fun getMemoryMatchingBestTime(): Long?
}
