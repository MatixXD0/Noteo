package com.example.noteo.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF03a9f4),
    onPrimary = Color.Black,
    primaryContainer = Color(0xFF4dae52),
    onPrimaryContainer = Color.White,
    secondary = Color(0xFF03DAC5),
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFF018786),
    onSecondaryContainer = Color.White,
    background = Color.Black,
    onBackground = Color.White,
    surface = Color(0xFF49454f),
    onSurface = Color.White,
    error = Color(0xFFCF6679),
    onError = Color.Black,

)

@Composable
fun DarkTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        shapes = MaterialTheme.shapes,
        content = content
    )
}
