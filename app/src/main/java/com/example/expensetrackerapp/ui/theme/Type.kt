package com.example.expensetrackerapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography: Typography
    @Composable
    get() = Typography(
        bodyLarge = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.5.sp
    ),
        titleLarge = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            lineHeight = 28.sp,
            letterSpacing = 0.sp,
            color = MaterialTheme.colorScheme.tertiary
        ),
        titleMedium = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.2.sp,
            color = MaterialTheme.colorScheme.tertiary
        ),
        titleSmall = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.2.sp,
            color = MaterialTheme.colorScheme.tertiary
        ),
        labelMedium = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.2.sp,
            color = MaterialTheme.colorScheme.secondary
        ),
        labelLarge = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.1.sp,
            color = MaterialTheme.colorScheme.primary
        )
)