package com.jaidensiu.eggpedia.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val LightTextFieldPlaceholder = Color(color = 0xFFB0B0B0)
val DarkTextFieldPlaceholder = Color(color = 0xFF424242)

@Composable
fun TextFieldPlaceHolder() = if (isSystemInDarkTheme()) DarkTextFieldPlaceholder else LightTextFieldPlaceholder

val LightPrimary = Color(color = 0xFFFFC107)
val LightPrimaryVariant = Color(color = 0xFFFFA000)
val LightSecondary = Color(color = 0xFFFFEB3B)
val LightBackground = Color(color = 0xFFFFF8E1)
val LightSurface = Color(color = 0xFFFCFBF4)
val LightOnPrimary = Color(color = 0xFF000000)
val LightOnSecondary = Color(color = 0xFF000000)
val LightOnBackground = Color(color = 0xFF000000)
val LightOnSurface = Color(color = 0xFF000000)

val DarkPrimary = Color(color = 0xFFFFC107)
val DarkPrimaryVariant = Color(color = 0xFFFFA000)
val DarkSecondary = Color(color = 0xFFFFEB3B)
val DarkBackground = Color(color = 0xFF212121)
val DarkSurface = Color(color = 0xFF303030)
val DarkOnPrimary = Color(color = 0xFF000000)
val DarkOnSecondary = Color(color = 0xFF000000)
val DarkOnBackground = Color(color = 0xFFFFFFFF)
val DarkOnSurface = Color(color = 0xFFFFFFFF)

val LightColorPalette = lightColors(
    primary = LightPrimary,
    primaryVariant = LightPrimaryVariant,
    secondary = LightSecondary,
    background = LightBackground,
    surface = LightSurface,
    onPrimary = LightOnPrimary,
    onSecondary = LightOnSecondary,
    onBackground = LightOnBackground,
    onSurface = LightOnSurface
)

val DarkColorPalette = darkColors(
    primary = DarkPrimary,
    primaryVariant = DarkPrimaryVariant,
    secondary = DarkSecondary,
    background = DarkBackground,
    surface = DarkSurface,
    onPrimary = DarkOnPrimary,
    onSecondary = DarkOnSecondary,
    onBackground = DarkOnBackground,
    onSurface = DarkOnSurface
)
