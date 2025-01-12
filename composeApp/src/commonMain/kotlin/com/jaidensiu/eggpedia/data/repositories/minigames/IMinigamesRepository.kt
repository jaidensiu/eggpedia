package com.jaidensiu.eggpedia.data.repositories.minigames

import com.jaidensiu.eggpedia.app.Route

interface IMinigamesRepository {
    suspend fun saveMinigameBestTime(time: Long, route: Route)
    suspend fun getRecipeMatchingBestTime(): Long?
    suspend fun getMemoryMatchingBestTime(): Long?
}
