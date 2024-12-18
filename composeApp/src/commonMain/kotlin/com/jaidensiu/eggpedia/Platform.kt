package com.jaidensiu.eggpedia

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform