package com.jaidensiu.eggpedia.data.local

import androidx.room.RoomDatabaseConstructor

@Suppress(names = ["NO_ACTUAL_FOR_EXPECT"])
expect object EggDatabaseConstructor : RoomDatabaseConstructor<LocalEggsDatabase> {
    override fun initialize(): LocalEggsDatabase
}
