package com.jaidensiu.eggpedia.data.local

import androidx.room.RoomDatabase

expect class DatabaseInstance {
    fun create(): RoomDatabase.Builder<LocalDatabase>
}
