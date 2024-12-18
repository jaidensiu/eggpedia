package com.jaidensiu.eggpedia.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class RemoteEggsApi(private val client: HttpClient) : IRemoteEggsApi {
    override suspend fun getEggs(): List<Egg> {
        return try {
            client
                .get(urlString = BASE_URL + EGGS)
                .body()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    companion object {
        private const val BASE_URL = "https://jaidensiu.vercel.app"
        private const val EGGS = "/eggs.json"
    }
}
