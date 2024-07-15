package com.dedan.kalenderadat.util

import androidx.compose.ui.graphics.Color

fun translateColorString(colorString: String): Color {
     return when (colorString) {
        "red" -> Color.Red
        "green" -> Color.Green
        "blue" -> Color.Yellow
        "slate" -> Color.Gray
        "purple" -> Color.Yellow
        "indigo" -> Color.Yellow
        "pink" -> Color.Red
        else -> Color.Gray
    }
}