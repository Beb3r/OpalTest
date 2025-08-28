package com.gb.opaltest.core.design.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun AppLinearProgressIndicator(
    modifier: Modifier = Modifier,
    width: Dp,
    backgroundColor: Color,
    foregroundColor: Brush,
    progress: Float,
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress.coerceIn(0f, 1f),
    )

    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(30.dp))
            .height(7.dp)
            .background(backgroundColor)
            .width(width)
            .drawWithCache {
                onDrawWithContent {
                    drawContent()
                    drawRect(
                        size = Size(width = size.width * animatedProgress, height = size.height),
                        brush = foregroundColor,
                    )
                }
            }
    )
}
