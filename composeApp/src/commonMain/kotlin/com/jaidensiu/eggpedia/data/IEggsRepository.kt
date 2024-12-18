package com.jaidensiu.eggpedia.data

interface IEggsRepository {
    suspend fun getEggs(): List<Egg>
}
