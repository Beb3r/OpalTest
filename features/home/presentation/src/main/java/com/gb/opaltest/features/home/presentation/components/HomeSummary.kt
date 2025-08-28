package com.gb.opaltest.features.home.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gb.opaltest.core.common.dashedBorder
import com.gb.opaltest.core.design.Body
import com.gb.opaltest.core.design.Colors
import com.gb.opaltest.core.design.Label
import com.gb.opaltest.core.design.LocalAppThemeData
import com.gb.opaltest.core.design.TextSize
import com.gb.opaltest.core.design.TextWeight
import com.gb.opaltest.core.design.components.AppLinearProgressIndicator
import com.gb.opaltest.core.translations.toValue
import com.gb.opaltest.features.home.presentation.models.HomeCurrentRewardUiModel
import com.gb.opaltest.features.home.presentation.models.HomeReferredUserUiModel
import kotlinx.collections.immutable.PersistentList
import com.gb.opaltest.core.design.R.drawable as drawables
import com.gb.opaltest.core.translations.R.string as translations

@Composable
fun HomeSummary(
    modifier: Modifier,
    currentReward: HomeCurrentRewardUiModel,
    referredUsers: PersistentList<HomeReferredUserUiModel>,
) {
    val context = LocalContext.current
    val density = LocalDensity.current

    var detailsContainerWidth by remember {
        mutableStateOf(0.dp)
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .dashedBorder(
                brush = SolidColor(Colors.MediumGrey),
                shape = RoundedCornerShape(16.dp)
            )
            .background(color = Colors.Black, shape = RoundedCornerShape(16.dp)),
    ) {
        if (currentReward is HomeCurrentRewardUiModel.Visible) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(IntrinsicSize.Min),
            ) {
                Image(
                    modifier = Modifier.clip(RoundedCornerShape(16.dp)),
                    painter = painterResource(currentReward.imageDrawableResId),
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
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Label(
                        text = currentReward.subtitle.toValue(context = context),
                        textWeight = TextWeight.SEMI_BOLD,
                        textSize = TextSize.MEDIUM,
                        textColor = Colors.LightGrey,
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Body(
                            modifier = Modifier.weight(1f),
                            text = currentReward.title.toValue(context = context),
                            textWeight = TextWeight.SEMI_BOLD,
                            textSize = TextSize.LARGE,
                            textColor = Colors.White,
                        )

                        if (currentReward is HomeCurrentRewardUiModel.Visible.UnClaimed) {
                            Label(
                                modifier = Modifier
                                    .background(Colors.White, shape = RoundedCornerShape(16.dp))
                                    .clickable {
                                        currentReward.onClick(currentReward.id)
                                    }
                                    .padding(horizontal = 24.dp, vertical = 6.dp),
                                text = stringResource(translations.reward_button_claim),
                                textWeight = TextWeight.BOLD,
                                textSize = TextSize.MEDIUM,
                                textColor = Colors.Black,
                            )
                        } else if (currentReward is HomeCurrentRewardUiModel.Visible.InProgress) {
                            Label(
                                text = "${currentReward.progress}/${currentReward.total}",
                                textWeight = TextWeight.BOLD,
                                textSize = TextSize.MEDIUM,
                                textColor = Colors.LightGrey,
                            )
                        }
                    }
                    if (currentReward is HomeCurrentRewardUiModel.Visible.InProgress) {
                        val progress by remember(currentReward.progress, currentReward.total) {
                            mutableFloatStateOf(
                                currentReward.progress / currentReward.total.toFloat()
                            )
                        }
                        val localAppThemeData = LocalAppThemeData.current
                        AppLinearProgressIndicator(
                            modifier = Modifier.padding(top = 6.dp),
                            width = detailsContainerWidth,
                            backgroundColor = Colors.MediumGrey,
                            foregroundColor = Brush.horizontalGradient(
                                listOf(
                                    localAppThemeData.mainColor,
                                    localAppThemeData.secondaryColor
                                )
                            ),
                            progress = progress,
                        )
                    }
                }
            }

            Canvas(
                Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .height(2.dp)
            ) {
                drawLine(
                    color = Colors.MediumGrey,
                    strokeWidth = 2.dp.toPx(),
                    start = Offset(0f, 2.dp.toPx() / 2),
                    end = Offset(size.width, 2.dp.toPx() / 2),
                    pathEffect = PathEffect.dashPathEffect(
                        intervals = floatArrayOf(6.dp.toPx(), 2.dp.toPx())
                    )
                )
            }

            Row(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Body(
                    modifier = Modifier.weight(1f),
                    text = stringResource(translations.referral_referee_count),
                    textWeight = TextWeight.SEMI_BOLD,
                    textSize = TextSize.LARGE,
                    textColor = Colors.White,
                )

                Icon(
                    painter = painterResource(drawables.ic_user),
                    contentDescription = "referred users icon",
                    tint = Colors.White,
                )

                Body(
                    text = referredUsers.size.toString(),
                    textWeight = TextWeight.SEMI_BOLD,
                    textSize = TextSize.LARGE,
                    textColor = Colors.White,
                )
            }

            if (referredUsers.isNotEmpty()) {
                var userContainerHeight by remember {
                    mutableStateOf(0.dp)
                }
                var isExpanded by remember {
                    mutableStateOf(false)
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    LazyColumn(
                        Modifier
                            .fillMaxWidth()
                            .heightIn(
                                max = if (isExpanded)
                                    userContainerHeight * referredUsers.size
                                else
                                    userContainerHeight * 3
                            )
                            .padding(start = 16.dp, end = 16.dp)
                            .animateContentSize(),
                        userScrollEnabled = false,
                    ) {
                        items(items = referredUsers, key = { it.id }) { user ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .onPlaced {
                                        with(density) {
                                            val height = it.size.height.toDp()
                                            userContainerHeight = height + 12.dp
                                        }
                                    },
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Image(
                                    modifier = Modifier.size(32.dp),
                                    painter = painterResource(drawables.opal_user),
                                    contentDescription = "referred user avatar",
                                )

                                Body(
                                    modifier = Modifier
                                        .padding(horizontal = 16.dp, vertical = 4.dp)
                                        .weight(1f),
                                    text = user.name,
                                    textWeight = TextWeight.BOLD,
                                    textSize = TextSize.LARGE,
                                    textColor = Colors.White,
                                )

                                Body(
                                    text = user.signUpdate.toValue(context),
                                    textWeight = TextWeight.SEMI_BOLD,
                                    textSize = TextSize.MEDIUM,
                                    textColor = Colors.LightGrey,
                                )
                            }
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()

                            .align(Alignment.BottomCenter)
                            .background(
                                brush = Brush.verticalGradient(
                                    colorStops = arrayOf(
                                        0.0f to Colors.Transparent,
                                        0.2f to Colors.Black,
                                    ),
                                )
                            )
                            .clickable {
                                isExpanded = !isExpanded
                            }
                            .padding(bottom = 12.dp),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        Icon(
                            painter = painterResource(if (isExpanded) drawables.ic_keyboard_arrow_up else drawables.ic_keyboard_arrow_down),
                            contentDescription = "",
                            tint = Colors.White,
                        )
                    }
                }

            } else {
                Spacer(Modifier.height(16.dp))
            }
        }
    }
}
