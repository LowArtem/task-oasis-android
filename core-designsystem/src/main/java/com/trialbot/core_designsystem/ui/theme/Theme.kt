package com.trialbot.core_designsystem.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = MyColors.LightBrown,
    primaryVariant = MyColors.MiddleBrown,
    secondary = MyColors.AlmostWhite,
    secondaryVariant = MyColors.MiddleBrownVariant,
    background = MyColors.DarkGray,
    surface = MyColors.AlmostBlack,
    error = MyColors.Red,
    onPrimary = MyColors.Black,
    onSurface = MyColors.White,
    onBackground = MyColors.AlmostWhite
)

private val LightColorPalette = lightColors(
    primary = MyColors.LightBrown,
    primaryVariant = MyColors.MiddleBrown,
    secondary = MyColors.DarkBrown,
    secondaryVariant = MyColors.MiddleBrownVariant,
    background = MyColors.AlmostWhite,
    surface = MyColors.White,
    error = MyColors.Red,
    onPrimary = MyColors.Black,
    onSurface = MyColors.Black,
    onBackground = MyColors.DarkBrown
)

@Composable
fun TaskOasisTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
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