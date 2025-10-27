package com.example.noteo.ui.theme

import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF03a9f4),
    onPrimary = Color.White,
    primaryContainer = Color(0xFF5db861),
    onPrimaryContainer = Color.Black,
    secondary = Color(0xFF03DAC5),
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFF018786),
    onSecondaryContainer = Color.White,
    background = Color.White,
    onBackground = Color.Black,
    surface = Color(0xFFe7e0ec),
    onSurface = Color.Black,
    error = Color(0xFFB00020),
    onError = Color.White,

)

@Composable
fun LightTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        shapes = MaterialTheme.shapes,
        content = content
    )
}
