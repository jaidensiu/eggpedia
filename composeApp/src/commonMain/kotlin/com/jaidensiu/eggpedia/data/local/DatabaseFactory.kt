package com.jaidensiu.eggpedia.data.local

import androidx.room.RoomDatabase

expect class DatabaseFactory {
    fun create(): RoomDatabase.Builder<LocalDatabase>
}
