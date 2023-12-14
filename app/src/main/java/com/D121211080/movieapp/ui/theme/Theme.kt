package com.D121211080.movieapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColorScheme(
    primary = Purple80,
    secondary = Pink80,
    background = Black80,
    surface = Black80,
    onPrimary = Color.White,
    onSecondary = Black80,
    onBackground = Color.White,
    onSurface = Color.White
)


@Composable
fun MovieAppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorPalette,
        typography = Typography,
        content = content
    )
}