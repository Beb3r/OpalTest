package com.gb.opaltest.features.home.presentation

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType.Companion.PrimaryNotEditable
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gb.opaltest.core.common.LazyColumnScrollObserver
import com.gb.opaltest.core.design.Body
import com.gb.opaltest.core.design.Colors
import com.gb.opaltest.core.design.TextSize
import com.gb.opaltest.core.design.TextWeight
import com.gb.opaltest.core.design.Title
import com.gb.opaltest.core.design.components.PrimaryButton
import com.gb.opaltest.core.design.components.SecondaryButton
import com.gb.opaltest.core.translations.toValue
import com.gb.opaltest.features.home.presentation.components.HomeReferralCard
import com.gb.opaltest.features.home.presentation.components.HomeReferralCode
import com.gb.opaltest.features.home.presentation.components.HomeReward
import com.gb.opaltest.features.home.presentation.components.HomeSettingsBottomSheet
import com.gb.opaltest.features.home.presentation.components.HomeSummary
import com.gb.opaltest.features.home.presentation.models.HomeEventsUiModel
import com.gb.opaltest.features.home.presentation.models.HomeViewStateUiModel
import org.koin.androidx.compose.koinViewModel
import com.gb.opaltest.core.design.R.drawable as drawables
import com.gb.opaltest.core.translations.R.string as translations

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    onScrolled: (Float) -> Unit,
) {
    val context = LocalContext.current
    LaunchedEffect(viewModel) {
        viewModel.eventsFlow.collect { event ->
            when (event) {
                is HomeEventsUiModel.ShareLink -> {
                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, event.message.toValue(context))
                        type = "text/plain"
                    }

                    val shareIntent = Intent.createChooser(sendIntent, null)
                    context.startActivity(shareIntent)
                }
            }
        }
    }

    val viewState by viewModel.viewState.collectAsStateWithLifecycle()

    HomeScreenContent(
        viewState = viewState,
        onScrolled = onScrolled,
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    viewState: HomeViewStateUiModel,
    onScrolled: (Float) -> Unit
) {
    val density = LocalDensity.current

    val listState = rememberLazyListState()
    LazyColumnScrollObserver(
        listState = listState,
        onScrollOffsetChanged = { offset ->
            onScrolled(offset.toFloat())
        }
    )

    var isMenuExpanded by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        state = listState,
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
            ) {
                ExposedDropdownMenuBox(
                    modifier = Modifier.align(alignment = Alignment.CenterEnd),
                    expanded = isMenuExpanded,
                    onExpandedChange = { }
                ) {
                    IconButton(
                        modifier = Modifier.align(alignment = Alignment.CenterEnd).menuAnchor(type = PrimaryNotEditable),
                        onClick = { isMenuExpanded = !isMenuExpanded }
                    ) {
                        Icon(
                            painter = painterResource(drawables.ic_more_vert),
                            contentDescription = "",
                            tint = Colors.White
                        )
                    }
                    DropdownMenu(
                        modifier = Modifier.align(alignment = Alignment.CenterEnd),
                        expanded = isMenuExpanded,
                        onDismissRequest = { isMenuExpanded = false },
                    ) {
                        DropdownMenuItem(
                            text = {
                                Body(
                                    text = stringResource(translations.home_settings_simulate_referral),
                                    textColor = Colors.White,
                                )
                            },
                            onClick = {
                                viewState.onSettingsSimulateReferralsClicked()
                                isMenuExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Body(
                                    text = stringResource(translations.home_settings_pick_gem),
                                    textColor = Colors.White,
                                )
                            },
                            onClick = {
                                viewState.onSettingsPickGemClicked()
                                isMenuExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Body(
                                    text = stringResource(translations.home_settings_clear),
                                    textColor = Colors.White,
                                )
                            },
                            onClick = {
                                viewState.onSettingsClearClicked()
                                isMenuExpanded = false
                            }
                        )
                    }
                }
            }
            HomeReferralCard()
            Title(
                modifier = Modifier.padding(top = 32.dp),
                text = stringResource(translations.home_referral_subtitle),
                textAlign = TextAlign.Center,
                textWeight = TextWeight.SEMI_BOLD,
                textSize = TextSize.LARGE,
            )
            Body(
                modifier = Modifier
                    .padding(top = 48.dp)
                    .fillMaxWidth(),
                text = stringResource(translations.home_referral_code),
                textAlign = TextAlign.Center,
                textWeight = TextWeight.SEMI_BOLD,
                textSize = TextSize.LARGE,
                textColor = Colors.LightGrey
            )
        }

        item {
            HomeReferralCode(
                referralCode = viewState.referralCode,
            )
        }

        item {
            PrimaryButton(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                content = {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(drawables.ic_add_friend),
                        contentDescription = null,
                        tint = Colors.Black,
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Body(
                        text = stringResource(translations.home_referral_button_add_friend),
                        textAlign = TextAlign.Center,
                        textWeight = TextWeight.SEMI_BOLD,
                        textSize = TextSize.LARGE,
                        textColor = Colors.Black,
                    )
                },
                onClick = {
                    viewState.onAddFriendButtonClicked(viewState.referralCode)
                }
            )
            SecondaryButton(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .fillMaxWidth(),
                content = {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(drawables.ic_share),
                        contentDescription = null,
                        tint = Colors.Black,
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Body(
                        text = stringResource(translations.home_referral_button_share_link),
                        textAlign = TextAlign.Center,
                        textWeight = TextWeight.SEMI_BOLD,
                        textSize = TextSize.LARGE,
                        textColor = Colors.Black,
                    )
                },
                onClick = {
                    viewState.onShareLinkButtonClicked(viewState.referralCode)
                }
            )
        }

        item {
            HomeSummary(
                modifier = Modifier
                    .padding(top = 12.dp, bottom = 48.dp),
                currentReward = viewState.currentReward,
                referredUsers = viewState.referredUsers,
            )
        }

        itemsIndexed(items = viewState.rewards, key = { _, reward -> reward.id }) { index, reward ->
            val isLast = index == viewState.rewards.size - 1
            val bottomPadding =
                if (isLast) {
                    with(density) {
                        WindowInsets.safeContent.getBottom(density).toDp()
                    }
                } else {
                    10.dp
                }

            HomeReward(
                reward = reward,
                isLast = { isLast },
                bottomPadding = { bottomPadding },
            )
        }
    }

    HomeSettingsBottomSheet(
        viewState = viewState.settingsBottomSheetViewState,
    )
}
