package com.jaidensiu.eggpedia.data

import com.jaidensiu.eggpedia.data.local.EggEntity
import com.jaidensiu.eggpedia.data.remote.EggDto

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
