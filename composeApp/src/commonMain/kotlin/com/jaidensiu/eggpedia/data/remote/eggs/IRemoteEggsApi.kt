package com.jaidensiu.eggpedia.data.remote.eggs

interface IRemoteEggsApi {
    suspend fun getEggs(): List<EggDto>
}
