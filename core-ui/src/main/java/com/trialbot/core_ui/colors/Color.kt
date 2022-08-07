package com.trialbot.core_ui.colors

import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color

object MyColors {
    val MiddleBrown: Color = Color(0xFFB48963)
    val Black: Color = Color(0xFF000000)
    val AlmostBlack: Color = Color(0xFF313131)
    val DarkGray: Color = Color(0xFF424242)
    val DarkBrown: Color = Color(0xFF544037)
    //val MiddleBrownVariant = Color(0xFF87674E)
    val LightBrown: Color = Color(0xFFE7B07A)
    val AlmostWhite: Color = Color(0xFFF0ECE3)
    val White: Color = Color(0xFFFFFFFF)
    val MiddleGray: Color = Color(0xFF969696)
    val Gray: Color = Color(0xFFB4B3B3)
    val Blue: Color = Color(0xFF0C76D9)
    val Yellow: Color = Color(0xFFF3B300)
    val Red: Color = Color(0xFFDA4437)
    val Green: Color = Color(0xFF0F9D58)
}


val Colors.infoColor: Color
    get() = MyColors.Blue

val Colors.attentionColor: Color
    get() = MyColors.Yellow

val Colors.goodColor: Color
    get() = MyColors.Green

val Colors.disabledColor: Color
    get() = MyColors.MiddleGray

val Colors.inputStrokeColor: Color
    get() = MyColors.Gray