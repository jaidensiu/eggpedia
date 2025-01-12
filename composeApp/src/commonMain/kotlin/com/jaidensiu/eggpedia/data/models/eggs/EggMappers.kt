package com.jaidensiu.eggpedia.data.models.eggs

import com.jaidensiu.eggpedia.data.local.eggs.EggEntity
import com.jaidensiu.eggpedia.data.remote.eggs.EggDto

object EggMappers {
    fun EggDto.toEgg(): Egg {
        return Egg(
            id = id,
            name = name,
            imageUrl = imageUrl,
            videoUrl = videoUrl,
            cookingSteps = cookingSteps
        )
    }

    fun Egg.toEggEntity(): EggEntity {
        return EggEntity(
            id = id.toString(),
            name = name,
            imageUrl = imageUrl,
            videoUrl = videoUrl,
            cookingSteps = cookingSteps
        )
    }

    fun EggEntity.toEgg(): Egg {
        return Egg(
            id = id.toInt(),
            name = name,
            imageUrl = imageUrl,
            videoUrl = videoUrl,
            cookingSteps = cookingSteps
        )
    }
}
