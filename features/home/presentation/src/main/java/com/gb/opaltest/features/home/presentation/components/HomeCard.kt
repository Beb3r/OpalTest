package com.gb.opaltest.features.home.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.gb.opaltest.core.common.sensor.SensorAnalyzer
import com.gb.opaltest.core.design.R.drawable as drawables

@Composable
fun HomeReferralCard(
    modifier: Modifier = Modifier,
) {

    var pitch by remember { mutableFloatStateOf(0f) }
    var roll by remember { mutableFloatStateOf(0f) }

    SensorAnalyzer {
        pitch = (it.pitch/4).coerceIn(-2f, 2f)
        roll = (it.roll/4).coerceIn(-2f, 2f)
    }

    Card(
        modifier = modifier
            .graphicsLayer {
                rotationY = -roll
                rotationX = -pitch
            },
        shape = RoundedCornerShape(24.dp)
    ) {
        AsyncImage(
            modifier = modifier
                .fillMaxWidth()
                .height(256.dp),
            model = drawables.referral_card,
            contentDescription = "Referral top card",
            contentScale = ContentScale.FillHeight
        )
    }
}

@PreviewScreenSizes
@Composable
fun HomeReferralCardPreview() {
    HomeReferralCard()
}
