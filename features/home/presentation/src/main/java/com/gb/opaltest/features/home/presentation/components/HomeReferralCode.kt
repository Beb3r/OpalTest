package com.gb.opaltest.features.home.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import com.gb.opaltest.core.design.AppThemeData
import com.gb.opaltest.core.design.Body
import com.gb.opaltest.core.design.Colors
import com.gb.opaltest.core.design.LocalAppThemeData
import com.gb.opaltest.core.design.TextSize
import com.gb.opaltest.core.design.TextWeight

@Composable
fun HomeReferralCode(
    modifier: Modifier = Modifier,
    referralCode: String
) {
    val localAppThemeData = LocalAppThemeData.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
    )
    {
        referralCode.toCharArray().forEach { char ->
            Card(
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(
                    width = 1.dp,
                    brush = Brush.verticalGradient(
                        listOf(
                            Colors.Black,
                            Colors.LightGrey.copy(alpha = 0.3f),
                        )
                    )
                ),
                colors = CardDefaults.cardColors(
                    containerColor = Colors.DarkGrey,
                )
            ) {
                Body(
                    modifier = Modifier
                        .padding(vertical = 16.dp, horizontal = 18.dp)
                        .graphicsLayer(alpha = 0.99f)
                        .drawWithCache {
                            val brush = Brush.horizontalGradient(
                                listOf(
                                    localAppThemeData.mainColor,
                                    localAppThemeData.secondaryColor,
                                )
                            )
                            onDrawWithContent {
                                drawContent()
                                drawRect(brush, blendMode = BlendMode.SrcAtop)
                            }
                        },
                    text = char.toString(),
                    textAlign = TextAlign.Center,
                    textWeight = TextWeight.SEMI_BOLD,
                    textSize = TextSize.LARGE,
                    textColor = Colors.LightGrey,
                )
            }
        }
    }
}

@PreviewScreenSizes
@Composable
fun HomeReferralCodePreview() {
    CompositionLocalProvider(LocalAppThemeData provides AppThemeData(
        mainColor = Colors.LightGreen,
        secondaryColor = Colors.LightBlue,
    )) {
        HomeReferralCode(
            referralCode = "A1B2C"
        )
    }
}
