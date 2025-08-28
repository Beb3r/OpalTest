package com.gb.opaltest.features.home.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.gb.opaltest.core.design.R.drawable as drawables

@Composable
fun HomeReferralCard(
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
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
