package com.gb.opaltest.features.home.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
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
import com.gb.opaltest.features.home.presentation.components.HomeAddFriendButton
import com.gb.opaltest.features.home.presentation.components.HomeReferralCard
import com.gb.opaltest.features.home.presentation.components.HomeReferralCode
import com.gb.opaltest.features.home.presentation.components.HomeReward
import com.gb.opaltest.features.home.presentation.components.HomeSettingsBottomSheet
import com.gb.opaltest.features.home.presentation.components.HomeShareButton
import com.gb.opaltest.features.home.presentation.models.HomeRewardUiModel
import com.gb.opaltest.features.home.presentation.models.HomeSettingsBottomSheetViewState
import kotlinx.collections.immutable.PersistentList
import org.koin.androidx.compose.koinViewModel
import com.gb.opaltest.core.design.R.drawable as drawables
import com.gb.opaltest.core.translations.R.string as translations

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    onScrolled: (Float) -> Unit,
) {

    val viewState by viewModel.viewState.collectAsStateWithLifecycle()

    HomeScreenContent(
        referralCode = viewState.referralCode,
        referredUsersCount = viewState.referredUsersCount,
        rewards = viewState.rewards,
        onSettingsClicked = viewState.onSettingsClicked,
        settingsBottomSheetViewState = viewState.settingsBottomSheetViewState,
        onScrolled = onScrolled,
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    referralCode: String,
    referredUsersCount: String,
    rewards: PersistentList<HomeRewardUiModel>,
    onSettingsClicked: () -> Unit,
    settingsBottomSheetViewState: HomeSettingsBottomSheetViewState,
    onScrolled: (Float) -> Unit,
) {
    val density = LocalDensity.current

    val listState = rememberLazyListState()
    LazyColumnScrollObserver(
        listState = listState,
        onScrollOffsetChanged = { offset ->
            onScrolled(offset.toFloat())
        }
    )

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
                IconButton(
                    modifier = Modifier.align(alignment = androidx.compose.ui.Alignment.CenterEnd),
                    onClick = onSettingsClicked,
                ) {
                    Icon(
                        painter = painterResource(drawables.ic_settings),
                        contentDescription = "",
                        tint = Colors.White
                    )
                }
            }
            HomeReferralCard()
            Title(
                modifier = Modifier.padding(top = 32.dp),
                text = stringResource(translations.referral_subtitle),
                textAlign = TextAlign.Center,
                textWeight = TextWeight.SEMI_BOLD,
                textSize = TextSize.LARGE,
            )
            Body(
                modifier = Modifier
                    .padding(top = 48.dp)
                    .fillMaxWidth(),
                text = stringResource(translations.referral_code),
                textAlign = TextAlign.Center,
                textWeight = TextWeight.SEMI_BOLD,
                textSize = TextSize.LARGE,
                textColor = Colors.LightGrey
            )
        }

        item {
            HomeReferralCode(
                referralCode = referralCode,
            )
        }

        item {
            HomeAddFriendButton(onClick = {})
            HomeShareButton(onClick = {})
        }

        itemsIndexed(items = rewards, key = { _, reward -> reward.id }) { index, reward ->
            val isLast = index == rewards.size - 1
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
        viewState = settingsBottomSheetViewState,
    )
}
