package com.jaidensiu.eggpedia.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun EggpediaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorPalette else LightColorPalette

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = colors.background
    ) {
        MaterialTheme(
            colors = colors,
            content = content
        )
    }
}
