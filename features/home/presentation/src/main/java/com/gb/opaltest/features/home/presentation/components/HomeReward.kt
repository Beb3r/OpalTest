package com.gb.opaltest.features.home.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.gb.opaltest.core.design.Body
import com.gb.opaltest.core.design.Colors
import com.gb.opaltest.core.design.Label
import com.gb.opaltest.core.design.components.AppLinearProgressIndicator
import com.gb.opaltest.core.design.LocalAppThemeData
import com.gb.opaltest.core.design.TextSize
import com.gb.opaltest.core.design.TextWeight
import com.gb.opaltest.core.translations.toValue
import com.gb.opaltest.features.home.presentation.models.HomeRewardFooterUiModel
import com.gb.opaltest.features.home.presentation.models.HomeRewardUiModel
import com.gb.opaltest.core.design.R.drawable as drawables

@Composable
fun HomeReward(
    reward: HomeRewardUiModel,
    isLast: () -> Boolean,
    bottomPadding: () -> Dp,
) {
    val localAppThemeData = LocalAppThemeData.current
    val context = LocalContext.current
    val density = LocalDensity.current

    var detailsContainerWidth by remember {
        mutableStateOf(0.dp)
    }

    Column(
        modifier = Modifier
            .padding(bottom = bottomPadding())
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = Colors.MediumGrey,
                    shape = RoundedCornerShape(16.dp)
                )
                .background(color = Colors.Black, shape = RoundedCornerShape(16.dp))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                modifier = Modifier.clip(RoundedCornerShape(16.dp)),
                painter = painterResource(reward.imageDrawableResId),
                contentDescription = "reward image",
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
                    .onPlaced { layoutCoordinates ->
                        with(density) {
                            detailsContainerWidth = layoutCoordinates.size.width.toDp()
                        }
                    }
            ) {
                Label(
                    modifier = Modifier
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
                    text = reward.friendsCount.toValue(context = context),
                    textWeight = TextWeight.BOLD,
                    textSize = TextSize.MEDIUM,
                    textColor = Colors.LightGrey,
                )
                Body(
                    modifier = Modifier.padding(top = 5.dp),
                    text = reward.title.toValue(context = context),
                    textWeight = TextWeight.SEMI_BOLD,
                    textSize = TextSize.LARGE,
                    textColor = Colors.White,
                )
                Label(
                    modifier = Modifier.padding(top = 5.dp),
                    text = reward.subtitle.toValue(context = context),
                    textWeight = TextWeight.SEMI_BOLD,
                    textSize = TextSize.MEDIUM,
                    textColor = Colors.LightGrey,
                )
                HomeRewardFooter(
                    modifier = Modifier.padding(top = 8.dp),
                    footer = reward.footer,
                    detailsContainerWidth = detailsContainerWidth
                )
            }
        }
        if (!isLast()) {
            Icon(
                modifier = Modifier.padding(top = 10.dp),
                painter = painterResource(drawables.ic__arrow_downward),
                contentDescription = "reward image",
                tint = Colors.MediumGrey,
            )
        }
    }
}

@Composable
fun HomeRewardFooter(
    modifier: Modifier,
    footer: HomeRewardFooterUiModel,
    detailsContainerWidth: Dp,
) {
    when (footer) {
        is HomeRewardFooterUiModel.InProgress -> {
            val localAppThemeData = LocalAppThemeData.current
            AppLinearProgressIndicator(
                modifier = modifier,
                width = detailsContainerWidth,
                backgroundColor = Colors.MediumGrey,
                foregroundColor = Brush.horizontalGradient(
                    listOf(
                        localAppThemeData.mainColor,
                        localAppThemeData.secondaryColor
                    )
                ),
                progress = footer.progress,
            )
        }

        is HomeRewardFooterUiModel.Unlocked -> {

        }
    }

}