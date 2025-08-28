package com.gb.opaltest.core.design.components

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.allowHardware
import coil3.request.transformations
import com.gb.opaltest.core.design.LocalAppThemeData
import com.gb.opaltest.core.design.image.BlurTransformation

@Composable
fun BoxScope.AmbientBackground(
    modifier: Modifier = Modifier,
    offset: Float,
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current
    val density = LocalDensity.current

    AsyncImage(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(fraction = 0.5f)
            .graphicsLayer {
                scaleY = 1.5f
                scaleX = 1.3f
                with(density) {
                    translationY = (-32).dp.toPx() + (-offset)
                }
            },
        model = ImageRequest.Builder(context)
            .data(LocalAppThemeData.current.ambientBackgroundGemDrawableResId)
            .allowHardware(false)
            .apply {
                transformations(
                    listOf(
                        BlurTransformation(
                            context = context,
                            radius = 25f,
                            sampling = 8f,
                        )
                    )
                )
            }.build(),
        contentDescription = "ambient background",
        contentScale = ContentScale.Crop,
    )

    content()
}
