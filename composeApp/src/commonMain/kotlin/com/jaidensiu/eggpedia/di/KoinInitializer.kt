package com.jaidensiu.eggpedia.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

object KoinInitializer {
    fun initKoin(config: KoinAppDeclaration? = null) {
        startKoin {
            config?.invoke(this)
            modules(sharedModule, platformModule)
        }
    }
}
