package com.jaidensiu.eggpedia.data

import com.jaidensiu.eggpedia.data.remote.EggDto

object EggMappers {
    fun EggDto.toEgg(): Egg {
        return Egg(
            name = name,
            imageUrl = imageUrl,
            videoUrl = videoUrl,
            cookingSteps = cookingSteps
        )
    }
}
