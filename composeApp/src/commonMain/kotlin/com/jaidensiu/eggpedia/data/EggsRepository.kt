package com.jaidensiu.eggpedia.data

import com.jaidensiu.eggpedia.data.EggMappers.toEgg
import com.jaidensiu.eggpedia.data.remote.RemoteEggsApi

class EggsRepository(private val remoteEggsApi: RemoteEggsApi) : IEggsRepository {
    override suspend fun getEggs(): List<Egg> {
        return remoteEggsApi.getEggs().map { eggDto -> eggDto.toEgg() }
    }
}
