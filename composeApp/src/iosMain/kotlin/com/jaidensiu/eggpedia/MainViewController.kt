package com.jaidensiu.eggpedia

import androidx.compose.ui.window.ComposeUIViewController
import com.jaidensiu.eggpedia.di.KoinInitializer.initKoin

fun MainViewController() = ComposeUIViewController(configure = { initKoin() }) {
    App()
}
