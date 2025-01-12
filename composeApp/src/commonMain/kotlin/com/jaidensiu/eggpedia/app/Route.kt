package com.jaidensiu.eggpedia.app

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object RouteGraph : Route

    @Serializable
    data object Home : Route

    @Serializable
    data object EggsList : Route

    @Serializable
    data object SavedEggsList : Route

    @Serializable
    data class EggDetails(val id: String) : Route

    @Serializable
    data object EggMinigames : Route

    @Serializable
    data object RecipeMatchingMinigame : Route

    @Serializable
    data object MemoryMatchingMinigame : Route
}
