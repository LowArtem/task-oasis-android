package com.trialbot.taskoasis.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Colors.infoColor: Color
    get() = Blue

val Colors.attentionColor: Color
    get() = Yellow

val Colors.goodColor: Color
    get() = Green

val Colors.disabledColor: Color
    get() = MiddleGray

val Colors.inputStrokeColor: Color
    get() = Gray

private val DarkColorPalette = darkColors(
    primary = LightBrown,
    primaryVariant = MiddleBrown,
    secondary = AlmostWhite,
    background = DarkGray,
    surface = AlmostBlack,
    error = Red,
    onPrimary = Black,
    onSurface = White,
    onBackground = AlmostWhite
)

private val LightColorPalette = lightColors(
    primary = LightBrown,
    primaryVariant = MiddleBrown,
    secondary = DarkBrown,
    background = AlmostWhite,
    surface = White,
    error = Red,
    onPrimary = Black,
    onSurface = Black,
    onBackground = DarkBrown
)

@Composable
fun TaskOasisTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}