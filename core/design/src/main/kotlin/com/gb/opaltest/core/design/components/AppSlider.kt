package com.gb.opaltest.core.design.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.awaitHorizontalTouchSlopOrCancellation
import androidx.compose.foundation.gestures.horizontalDrag
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.gb.opaltest.core.design.Colors
import com.kyant.liquidglass.GlassStyle
import com.kyant.liquidglass.LiquidGlassProviderState
import com.kyant.liquidglass.liquidGlass
import com.kyant.liquidglass.material.GlassMaterial
import com.kyant.liquidglass.refraction.InnerRefraction
import com.kyant.liquidglass.refraction.RefractionAmount
import com.kyant.liquidglass.refraction.RefractionHeight
import kotlinx.coroutines.launch

val CustomEasing: Easing = CubicBezierEasing(0.5f, 0.5f, 1.0f, 0.25f)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppSlider(
    modifier: Modifier = Modifier,
    state: SliderState,
    providerState: LiquidGlassProviderState,
) {

    val animatedValue by animateFloatAsState(
        targetValue = state.value,
        spring(stiffness = Spring.StiffnessHigh),
        label = "animatedValue",
    )

    var scaleX by remember { mutableFloatStateOf(1f) }
    var scaleY by remember { mutableFloatStateOf(1f) }
    var translateX by remember { mutableFloatStateOf(0f) }
    var transformOrigin by remember { mutableStateOf(TransformOrigin.Center) }

    Slider(
        state = state,
        modifier = modifier
            .graphicsLayer {
                this.transformOrigin = transformOrigin
                this.scaleX = scaleX
                this.scaleY = scaleY
                this.translationX = translateX
            },
        thumb = {},
        track = { sliderState ->
            val sliderFraction by remember {
                derivedStateOf {
                    (animatedValue - sliderState.valueRange.start) / (sliderState.valueRange.endInclusive - sliderState.valueRange.start)
                }
            }

            val density = LocalDensity.current

            Box(
                modifier = Modifier
                    .trackOverslide(value = sliderFraction) { overslide ->
                        transformOrigin = TransformOrigin(
                            pivotFractionX = if (sliderFraction < .5f) 2f else -1f,
                            pivotFractionY = .5f,
                        )

                        when (sliderFraction) {
                            in 0f..(.5f) -> {
                                scaleY = 1f + (overslide * .2f)
                                scaleX = 1f - (overslide * .2f)
                            }

                            else -> {
                                scaleY = 1f - (overslide * .2f)
                                scaleX = 1f + (overslide * .2f)
                            }
                        }

                        translateX = overslide * with(density) { 24.dp.toPx() }

                    }
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(20.dp))
                    .liquidGlass(
                        providerState,
                        GlassStyle(
                            shape = RoundedCornerShape(20.dp),
                            innerRefraction = InnerRefraction(
                                height = RefractionHeight(8.dp),
                                amount = RefractionAmount((-16).dp)
                            ),
                            material = GlassMaterial(
                                blurRadius = 2.dp,
                                brush = SolidColor(Colors.Black),
                                alpha = 0.0f
                            )
                        )
                    )

            ) {
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    val bigLinePadding = 32.dp.toPx()
                    val bigLineHeight = size.height - bigLinePadding
                    val bigLineStartYPosition = bigLinePadding / 2
                    val bigLineEndYPosition = bigLineStartYPosition + bigLineHeight

                    val smallLinePadding = 64.dp.toPx()
                    val smallLineHeight = size.height - smallLinePadding
                    val smallLineStartYPosition = smallLinePadding / 2
                    val smallLineEndYPosition = smallLineStartYPosition + smallLineHeight

                    val linesCount = (size.width / 8.dp.toPx()).toInt()

                    repeat(linesCount) { index ->
                        if (index != 0) {
                            val lineXPosition = index * 8.dp.toPx()
                            drawLine(
                                color = Colors.LightGrey.copy(alpha = if (index % 2 == 0) 0.6f else 0.4f),
                                strokeWidth = 2.dp.toPx(),
                                start = Offset(
                                    lineXPosition,
                                    if (index % 2 == 0) bigLineStartYPosition else smallLineStartYPosition
                                ),
                                end = Offset(
                                    lineXPosition,
                                    if (index % 2 == 0) bigLineEndYPosition else smallLineEndYPosition
                                ),
                            )
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth(sliderFraction)
                        .fillMaxHeight()
                        .background(
                            color = Colors.White,
                        )
                )
            }
        },
    )
}


@Composable
fun Modifier.trackOverslide(
    value: Float,
    onNewOverslideAmount: (Float) -> Unit,
): Modifier {

    val valueState = rememberUpdatedState(value)
    val scope = rememberCoroutineScope()
    val overslideAmountAnimatable = remember { Animatable(0f, .0001f) }
    var length by remember { mutableFloatStateOf(1f) }

    LaunchedEffect(Unit) {
        snapshotFlow { overslideAmountAnimatable.value }.collect {
            onNewOverslideAmount(CustomEasing.transform(it / length))
        }
    }

    return onSizeChanged { length = it.width.toFloat() }
        .pointerInput(Unit) {
            awaitEachGesture {
                val down = awaitFirstDown()

                awaitHorizontalTouchSlopOrCancellation(down.id) { _, _ -> }

                var overslideAmount = 0f

                horizontalDrag(down.id) {
                    val deltaX = it.positionChange().x

                    overslideAmount = when (valueState.value) {
                        0f -> (overslideAmount + deltaX).coerceAtMost(0f)
                        1f -> (overslideAmount + deltaX).coerceAtLeast(1f)
                        else -> 0f
                    }

                    scope.launch {
                        overslideAmountAnimatable.animateTo(overslideAmount)
                    }
                }

                scope.launch {
                    overslideAmountAnimatable.animateTo(
                        targetValue = 0f,
                        animationSpec = spring(
                            dampingRatio = .45f,
                            stiffness = Spring.StiffnessMedium,
                        )
                    )
                }
            }
        }
}