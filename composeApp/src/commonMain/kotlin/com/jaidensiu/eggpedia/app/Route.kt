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
    data object EggQuizGames : Route

    @Serializable
    data object ImageMatchingGame : Route

    @Serializable
    data object CookingStepsOrderingGame : Route

    @Serializable
    data object MixOfQuestionsGame : Route
}
