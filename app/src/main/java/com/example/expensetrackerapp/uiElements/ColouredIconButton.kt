package com.example.expensetrackerapp.uiElements

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp

@Composable
fun ColouredIconButton(
    circleColour: androidx.compose.ui.graphics.Color,
    crossColour: androidx. compose. ui. graphics. Color,
    onClick: () -> Unit,
    size: Float = 84f,
    strokeWidth: Float = 10f
    ) {
    Box(
        modifier = Modifier
            .size(size.dp)
            .clickable(onClick = onClick)
            .padding(5.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(size.dp)) {
            drawCircle(color = circleColour)
            drawCross(crossColour, strokeWidth)
        }
    }
}

private fun DrawScope.drawCross(color: androidx. compose. ui. graphics. Color, strokeWidth: Float) {
    val padding = size.minDimension * 0.2f
    val centre = size.minDimension / 2

    drawLine(
        color = color,
        start = Offset(padding, centre),
        end = Offset(size.width - padding, centre),
        strokeWidth = strokeWidth,
        cap = StrokeCap.Round
    )
    drawLine(
        color = color,
        start = Offset(centre, padding),
        end = Offset(centre, size.height - padding),
        strokeWidth = strokeWidth,
        cap = StrokeCap.Round
    )
}