package com.multicategory.uniquequiz.ui.utils

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import com.multicategory.uniquequiz.ui.theme.ShimmerGradient_1
import com.multicategory.uniquequiz.ui.theme.ShimmerGradient_2
import com.multicategory.uniquequiz.ui.theme.ShimmerGradient_3

val AMOUNT_QUESTIONS = 5

fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember { mutableStateOf(IntSize.Zero) }
    val transition = rememberInfiniteTransition()
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000)
        )
    )
    background(
        brush = Brush.linearGradient(
            colors = listOf(
                ShimmerGradient_1,
                ShimmerGradient_2,
                ShimmerGradient_3
            ),
            start = Offset(x = startOffsetX, y = 0f),
            end = Offset(x = startOffsetX + size.width, y = size.height.toFloat())
        ),
        shape = MaterialTheme.shapes.medium
    ).onGloballyPositioned {
        size = it.size
    }
}
