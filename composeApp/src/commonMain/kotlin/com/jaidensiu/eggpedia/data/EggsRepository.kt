package com.jaidensiu.eggpedia.data

class EggsRepository(private val remoteEggsApi: RemoteEggsApi) : IEggsRepository {
    override suspend fun getEggs(): List<Egg> {
        return remoteEggsApi.getEggs()
    }
}
