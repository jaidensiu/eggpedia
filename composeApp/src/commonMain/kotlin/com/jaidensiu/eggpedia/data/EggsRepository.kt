package com.jaidensiu.eggpedia.data

import androidx.sqlite.SQLiteException
import com.jaidensiu.eggpedia.data.EggMappers.toEgg
import com.jaidensiu.eggpedia.data.EggMappers.toEggEntity
import com.jaidensiu.eggpedia.data.local.LocalEggDao
import com.jaidensiu.eggpedia.data.remote.RemoteEggsApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class EggsRepository(
    private val remoteEggsApi: RemoteEggsApi,
    private val localEggDao: LocalEggDao
) : IEggsRepository {
    override suspend fun getRemoteEggs(): List<Egg> {
        return remoteEggsApi.getEggs().map { eggDto -> eggDto.toEgg() }
    }

    override suspend fun getLocalEggs(): Flow<List<Egg>> {
        return localEggDao.getEggs().map { eggEntities -> eggEntities.map { it.toEgg() } }
    }

    override suspend fun saveEggToLocal(egg: Egg) {
        try {
            localEggDao.upsert(eggEntity = egg.toEggEntity())
        } catch (e: SQLiteException) {
            e.printStackTrace()
        }
    }

    override suspend fun removeEggFromLocal(id: String) {
        try {
            localEggDao.deleteSavedEgg(id)
        } catch (e: SQLiteException) {
            e.printStackTrace()
        }
    }

    override fun isEggSaved(id: String): Flow<Boolean> {
        return localEggDao.getEggs().map { eggEntities -> eggEntities.any { it.id == id } }
    }
}
