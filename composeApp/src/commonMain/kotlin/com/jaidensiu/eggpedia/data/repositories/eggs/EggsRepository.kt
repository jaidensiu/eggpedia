package com.jaidensiu.eggpedia.data.repositories.eggs

import androidx.sqlite.SQLiteException
import com.jaidensiu.eggpedia.data.models.eggs.EggMappers.toEgg
import com.jaidensiu.eggpedia.data.models.eggs.EggMappers.toEggEntity
import com.jaidensiu.eggpedia.data.local.eggs.EggsDao
import com.jaidensiu.eggpedia.data.models.eggs.Egg
import com.jaidensiu.eggpedia.data.remote.eggs.RemoteEggsApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class EggsRepository(
    private val remoteEggsApi: RemoteEggsApi,
    private val eggsDao: EggsDao
) : IEggsRepository {
    override suspend fun getRemoteEggs(): List<Egg> {
        return remoteEggsApi.getEggs().map { eggDto -> eggDto.toEgg() }
    }

    override suspend fun getLocalEggs(): Flow<List<Egg>> {
        return eggsDao.getEggs().map { eggEntities -> eggEntities.map { it.toEgg() } }
    }

    override suspend fun saveEggToLocal(egg: Egg) {
        try {
            eggsDao.upsert(eggEntity = egg.toEggEntity())
        } catch (e: SQLiteException) {
            e.printStackTrace()
        }
    }

    override suspend fun removeEggFromLocal(id: String) {
        try {
            eggsDao.deleteSavedEgg(id)
        } catch (e: SQLiteException) {
            e.printStackTrace()
        }
    }

    override fun isEggSaved(id: String): Flow<Boolean> {
        return eggsDao.getEggs().map { eggEntities -> eggEntities.any { it.id == id } }
    }
}
