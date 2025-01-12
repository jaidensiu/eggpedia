package com.jaidensiu.eggpedia.data.local.eggs

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface EggsDao {
    @Upsert
    suspend fun upsert(eggEntity: EggEntity)

    @Query("SELECT * FROM EggEntity")
    fun getEggs(): Flow<List<EggEntity>>

    @Query("DELETE FROM EggEntity WHERE id = :id")
    suspend fun deleteSavedEgg(id: String)
}
