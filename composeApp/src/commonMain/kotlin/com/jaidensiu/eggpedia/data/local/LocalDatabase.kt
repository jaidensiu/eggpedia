package com.jaidensiu.eggpedia.data.local

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jaidensiu.eggpedia.data.local.egg.EggDao
import com.jaidensiu.eggpedia.data.local.egg.EggEntity
import com.jaidensiu.eggpedia.data.local.minigame.MinigameDao
import com.jaidensiu.eggpedia.data.local.minigame.MinigameEntity

@Database(entities = [EggEntity::class, MinigameEntity::class], version = 1)
@TypeConverters(StringListTypeConverter::class)
@ConstructedBy(DatabaseConstructor::class)
abstract class LocalDatabase : RoomDatabase() {
    abstract val eggDao: EggDao
    abstract val minigameDao: MinigameDao

    companion object {
        const val DB_NAME = "eggpedia.db"
    }
}
