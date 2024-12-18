package com.jaidensiu.eggpedia.data

interface IRemoteEggsApi {
    suspend fun getEggs(): List<Egg>
}
