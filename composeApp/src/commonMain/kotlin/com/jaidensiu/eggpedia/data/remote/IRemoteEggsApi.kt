package com.jaidensiu.eggpedia.data.remote

interface IRemoteEggsApi {
    suspend fun getEggs(): List<EggDto>
}
