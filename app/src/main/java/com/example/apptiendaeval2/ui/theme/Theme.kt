package com.example.apptiendaeval2.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color

// Paleta de colores
private val DarkColorPalette = darkColors(
    primary = Color(0xFF4A4A4A),
    primaryVariant = Color(0xFF3A3A3A),
    secondary = Pink80
)

private val LightColorPalette = lightColors(
    primary = Color(0xFFB0B0B0),
    primaryVariant = Color(0xFF999999),
    secondary = Pink40
)

// Shapes
private val Shapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(8.dp),
    large = RoundedCornerShape(0.dp)
)

@Composable
fun AppTiendaEval2Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorPalette else LightColorPalette

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
