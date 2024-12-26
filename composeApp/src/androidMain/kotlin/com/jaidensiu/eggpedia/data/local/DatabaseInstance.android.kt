package com.jaidensiu.eggpedia.data.local

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

actual class DatabaseInstance(private val context: Context) {
    actual fun create(): RoomDatabase.Builder<LocalEggsDatabase> {
        val appContext = context.applicationContext
        val dbFile = appContext.getDatabasePath(LocalEggsDatabase.DB_NAME)

        return Room.databaseBuilder(context = appContext, name = dbFile.absolutePath)
    }
}
