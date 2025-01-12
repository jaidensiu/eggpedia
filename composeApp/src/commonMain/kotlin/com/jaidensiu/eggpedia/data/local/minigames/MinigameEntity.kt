package com.jaidensiu.eggpedia.data.local.minigames

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MinigameEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val recipeMatchingTime: Long?,
    val memoryMatchingTime: Long?
)
