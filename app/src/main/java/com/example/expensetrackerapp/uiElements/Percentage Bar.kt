package com.example.expensetrackerapp.uiElements

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PercentageBar(
    percentage: Double,
    barColour: Color = MaterialTheme.colorScheme.primaryContainer,
) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(12.dp)
            .padding(vertical = 4.dp)
    ) {
        val width = size.width
        val filledWidth = width * percentage.coerceIn(0.0, 1.0)

        drawRect(color = Color.LightGray.copy(alpha = 0.5f), size = size)
        drawRect(color = barColour, size = size.copy(width = filledWidth.toFloat()))
    }
}