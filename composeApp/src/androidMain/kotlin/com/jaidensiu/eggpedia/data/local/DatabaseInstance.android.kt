package com.jaidensiu.eggpedia.data.local

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

actual class DatabaseFactory(private val context: Context) {
    actual fun create(): RoomDatabase.Builder<LocalDatabase> {
        val appContext = context.applicationContext
        val dbFile = appContext.getDatabasePath(LocalDatabase.DB_NAME)

        return Room.databaseBuilder(context = appContext, name = dbFile.absolutePath)
    }
}
