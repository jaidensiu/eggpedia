package com.jaidensiu.eggpedia.data.repositories.egg

import androidx.sqlite.SQLiteException
import com.jaidensiu.eggpedia.data.models.egg.EggMappers.toEgg
import com.jaidensiu.eggpedia.data.models.egg.EggMappers.toEggEntity
import com.jaidensiu.eggpedia.data.local.egg.EggDao
import com.jaidensiu.eggpedia.data.models.egg.Egg
import com.jaidensiu.eggpedia.data.remote.egg.RemoteEggsApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class EggsRepository(
    private val remoteEggsApi: RemoteEggsApi,
    private val eggDao: EggDao
) : IEggsRepository {
    override suspend fun getRemoteEggs(): List<Egg> {
        return remoteEggsApi.getEggs().map { eggDto -> eggDto.toEgg() }
    }

    override suspend fun getLocalEggs(): Flow<List<Egg>> {
        return eggDao.getEggs().map { eggEntities -> eggEntities.map { it.toEgg() } }
    }

    override suspend fun saveEggToLocal(egg: Egg) {
        try {
            eggDao.upsert(eggEntity = egg.toEggEntity())
        } catch (e: SQLiteException) {
            e.printStackTrace()
        }
    }

    override suspend fun removeEggFromLocal(id: String) {
        try {
            eggDao.deleteSavedEgg(id)
        } catch (e: SQLiteException) {
            e.printStackTrace()
        }
    }

    override fun isEggSaved(id: String): Flow<Boolean> {
        return eggDao.getEggs().map { eggEntities -> eggEntities.any { it.id == id } }
    }
}
