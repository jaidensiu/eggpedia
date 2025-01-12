package com.jaidensiu.eggpedia.data.local

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jaidensiu.eggpedia.data.local.eggs.EggsDao
import com.jaidensiu.eggpedia.data.local.eggs.EggEntity
import com.jaidensiu.eggpedia.data.local.minigames.MinigamesDao
import com.jaidensiu.eggpedia.data.local.minigames.MinigameEntity

@Database(entities = [EggEntity::class, MinigameEntity::class], version = 1)
@TypeConverters(StringListTypeConverter::class)
@ConstructedBy(DatabaseConstructor::class)
abstract class LocalDatabase : RoomDatabase() {
    abstract val eggsDao: EggsDao
    abstract val minigamesDao: MinigamesDao

    companion object {
        const val DB_NAME = "eggpedia.db"
    }
}
