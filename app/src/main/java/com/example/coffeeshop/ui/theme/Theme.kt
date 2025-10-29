package com.example.coffeeshop.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = CoffeeAccent,
    secondary = BrownPrimary,
    background = BrownDark,
    onPrimary = BrownDark,
    onSecondary = CreamLight,
    onBackground = CreamLight
)

private val LightColorScheme = lightColorScheme(
    primary = BrownPrimary,
    secondary = CoffeeAccent,
    background = CreamLight,
    onPrimary = CreamLight,
    onSecondary = BrownDark,
    onBackground = BrownDark
)

@Composable
fun CoffeeShopTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}