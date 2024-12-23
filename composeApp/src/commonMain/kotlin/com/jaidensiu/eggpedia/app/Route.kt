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
    data class EggDetail(val id: String) : Route
}
