package com.jaidensiu.eggpedia.data.local.minigame

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MinigameEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val speedMatchingTime: Long?,
    val memoryMatchingTime: Long?
)
