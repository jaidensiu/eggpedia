package com.jaidensiu.eggpedia.data.models.egg

import com.jaidensiu.eggpedia.data.local.egg.EggEntity
import com.jaidensiu.eggpedia.data.remote.egg.EggDto

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
