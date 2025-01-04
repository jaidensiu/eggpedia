package com.jaidensiu.eggpedia.data.repositories.egg

import com.jaidensiu.eggpedia.data.models.egg.Egg
import kotlinx.coroutines.flow.Flow

interface IEggsRepository {
    suspend fun getRemoteEggs(): List<Egg>
    suspend fun getLocalEggs(): Flow<List<Egg>>
    suspend fun saveEggToLocal(egg: Egg)
    suspend fun removeEggFromLocal(id: String)
    fun isEggSaved(id: String): Flow<Boolean>
}
