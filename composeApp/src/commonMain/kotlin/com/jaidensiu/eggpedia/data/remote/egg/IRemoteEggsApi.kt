package com.jaidensiu.eggpedia.data.remote.egg

interface IRemoteEggsApi {
    suspend fun getEggs(): List<EggDto>
}
