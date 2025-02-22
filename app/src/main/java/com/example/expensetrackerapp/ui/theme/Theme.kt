package com.example.expensetrackerapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF46AAFB),
    onPrimary = Color.White,
    secondary = Color(0xFF6D95AF),
    onSecondary = Color.White,
    primaryContainer = Color(0xFF46AAFB),
    onPrimaryContainer = Color.White,
    tertiary = Color.White,
    surface = Color(0xFF121212),
    onSurface = Color.White,
    error = Color(0xFFCF6679),
    onError = Color.Black,
    background = Color(0xFF121212),
    onBackground = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF66ABE3),
    onPrimary = Color.White,
    secondary = Color(0xFF437493),
    onSecondary = Color.White,
    primaryContainer = Color(0xFF66ABE3),
    onPrimaryContainer = Color.White,
    tertiary = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,
    error = Color(0xFFB00020),
    onError = Color.White,
    background = Color.White,
    onBackground = Color.Black
)

@Composable
fun ExpenseTrackerAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}