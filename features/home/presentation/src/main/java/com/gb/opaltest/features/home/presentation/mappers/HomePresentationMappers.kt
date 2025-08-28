package com.gb.opaltest.features.home.presentation.mappers

import com.gb.opaltest.core.translations.R.plurals
import com.gb.opaltest.core.translations.TextUiModel
import com.gb.opaltest.features.home.domain.models.HomeDataDomainModel
import com.gb.opaltest.features.home.domain.models.HomeRewardDomainModel
import com.gb.opaltest.features.home.presentation.models.HomeRewardFooterUiModel
import com.gb.opaltest.features.home.presentation.models.HomeRewardUiModel
import com.gb.opaltest.features.home.presentation.models.HomeSettingsBottomSheetViewState
import com.gb.opaltest.features.home.presentation.models.HomeViewStateUiModel
import com.gb.opaltest.features.rewards.domain.models.RewardDomainModel.Companion.REWARD_ID_1
import com.gb.opaltest.features.rewards.domain.models.RewardDomainModel.Companion.REWARD_ID_2
import com.gb.opaltest.features.rewards.domain.models.RewardDomainModel.Companion.REWARD_ID_3
import com.gb.opaltest.features.rewards.domain.models.RewardDomainModel.Companion.REWARD_ID_4
import com.gb.opaltest.features.rewards.domain.models.RewardDomainModel.Companion.REWARD_ID_5
import kotlinx.collections.immutable.toPersistentList
import com.gb.opaltest.core.design.R.drawable as drawables
import com.gb.opaltest.core.translations.R.string as translations

fun HomeDataDomainModel.toHomeRewardUiModel(
    onSettingsClicked: () -> Unit,
    onClaimedRewardClicked: (String) -> Unit,
    shouldShowSettingsBottomSheet: Boolean,
    onSettingsBottomSheetClosed: () -> Unit,
    onSettingsBottomSheetButtonClicked: (Int) -> Unit,
): HomeViewStateUiModel {
    val settingsBottomSheetViewState = getSettingsBottomSheetViewState(
        shouldShowSettingsBottomSheet = shouldShowSettingsBottomSheet,
        referredUsersCount = this.referredUsersCount,
        onClosed = onSettingsBottomSheetClosed,
        onButtonClicked = onSettingsBottomSheetButtonClicked,
    )
    return HomeViewStateUiModel(
        referralCode = "X3FRR",
        referredUsersCount = this.referredUsersCount.toString(),
        onSettingsClicked = onSettingsClicked,
        rewards = this.rewards.map {
            it.toHomeRewardUiModel(onClaimedRewardClicked = onClaimedRewardClicked)
        }.toPersistentList(),
        settingsBottomSheetViewState = settingsBottomSheetViewState,
    )
}

fun HomeRewardDomainModel.toHomeRewardUiModel(
    onClaimedRewardClicked: (String) -> Unit,
): HomeRewardUiModel {
    val drawableResId = when (this.id) {
        REWARD_ID_1 -> drawables.reward_1
        REWARD_ID_2 -> drawables.reward_2
        REWARD_ID_3 -> drawables.reward_3
        REWARD_ID_4 -> drawables.reward_4
        REWARD_ID_5 -> drawables.reward_5
        else -> drawables.reward_6
    }
    val title = when (this.id) {
        REWARD_ID_1 -> translations.reward_1_title
        REWARD_ID_2 -> translations.reward_2_title
        REWARD_ID_3 -> translations.reward_3_title
        REWARD_ID_4 -> translations.reward_4_title
        REWARD_ID_5 -> translations.reward_5_title
        else -> translations.reward_6_title
    }
    val subtitle = when (this.id) {
        REWARD_ID_1 -> translations.reward_1_subtitle
        REWARD_ID_2 -> translations.reward_2_subtitle
        REWARD_ID_3 -> translations.reward_3_subtitle
        REWARD_ID_4 -> translations.reward_4_subtitle
        REWARD_ID_5 -> translations.reward_5_subtitle
        else -> translations.reward_6_subtitle
    }
    return HomeRewardUiModel(
        id = this.id,
        imageDrawableResId = drawableResId,
        friendsCount = TextUiModel.Plural(
            plurals.reward_threshold_count,
            this.threshold,
            arrayOf(this.threshold)
        ),
        title = TextUiModel.String(title),
        subtitle = TextUiModel.String(subtitle),
        footer = when (val state = this.state) {
            is HomeRewardDomainModel.HomeRewardStateDomainModel.InProgress -> HomeRewardFooterUiModel.InProgress(
                progress = state.progress / state.total.toFloat(),
            )

            is HomeRewardDomainModel.HomeRewardStateDomainModel.Claimed -> HomeRewardFooterUiModel.Unlocked.Claimed
            else -> HomeRewardFooterUiModel.Unlocked.UnClaimed(onClick = onClaimedRewardClicked)
        },
    )
}

private fun getSettingsBottomSheetViewState(
    shouldShowSettingsBottomSheet: Boolean,
    referredUsersCount: Int,
    onClosed: () -> Unit,
    onButtonClicked: (Int) -> Unit
): HomeSettingsBottomSheetViewState {
    return if (shouldShowSettingsBottomSheet) {
        HomeSettingsBottomSheetViewState.Visible(
            referredUsersCount = referredUsersCount,
            onClosed = onClosed,
            onButtonClicked = onButtonClicked,
        )
    } else {
        HomeSettingsBottomSheetViewState.Hidden
    }
}
