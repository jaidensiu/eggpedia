package com.jaidensiu.eggpedia.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class EggEntity(
    @PrimaryKey(autoGenerate = false) val id: String,
    val name: String,
    val imageUrl: String,
    val videoUrl: String,
    val cookingSteps: List<String>
)
