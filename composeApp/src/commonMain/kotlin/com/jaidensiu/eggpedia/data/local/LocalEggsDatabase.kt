package com.jaidensiu.eggpedia.data.local

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [EggEntity::class], version = 1)
@TypeConverters(StringListTypeConverter::class)
@ConstructedBy(EggDatabaseConstructor::class)
abstract class LocalEggsDatabase : RoomDatabase() {
    abstract val localEggDao: LocalEggDao

    companion object {
        const val DB_NAME = "eggs.db"
    }
}
