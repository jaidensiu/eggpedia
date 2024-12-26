package com.jaidensiu.eggpedia.app

import com.jaidensiu.eggpedia.data.Egg
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
}