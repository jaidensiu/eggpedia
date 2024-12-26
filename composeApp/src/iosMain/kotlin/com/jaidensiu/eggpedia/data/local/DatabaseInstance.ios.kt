@file:OptIn(ExperimentalForeignApi::class)

package com.jaidensiu.eggpedia.data.local

import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

actual class DatabaseInstance {
    actual fun create(): RoomDatabase.Builder<LocalEggsDatabase> {
        val dbFile = documentDirectory() + "/${LocalEggsDatabase.DB_NAME}"

        return Room.databaseBuilder<LocalEggsDatabase>(name = dbFile)
    }

    private fun documentDirectory(): String {
        val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null
        )

        return requireNotNull(documentDirectory?.path())
    }
}