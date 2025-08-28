package com.gb.opaltest.features.home.presentation.mappers

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.gb.opaltest.core.translations.R.plurals
import com.gb.opaltest.core.translations.TextUiModel
import com.gb.opaltest.features.home.domain.models.HomeDataDomainModel
import com.gb.opaltest.features.home.domain.models.HomeRewardDomainModel
import com.gb.opaltest.features.home.presentation.models.HomeCurrentRewardUiModel
import com.gb.opaltest.features.home.presentation.models.HomeReferredUserUiModel
import com.gb.opaltest.features.home.presentation.models.HomeRewardFooterUiModel
import com.gb.opaltest.features.home.presentation.models.HomeRewardUiModel
import com.gb.opaltest.features.home.presentation.models.HomeSettingsBottomSheetViewState
import com.gb.opaltest.features.home.presentation.models.HomeViewStateUiModel
import com.gb.opaltest.features.referral.domain.models.ReferredUserDomainModel
import com.gb.opaltest.features.rewards.domain.models.RewardDomainModel.Companion.REWARD_ID_1
import com.gb.opaltest.features.rewards.domain.models.RewardDomainModel.Companion.REWARD_ID_2
import com.gb.opaltest.features.rewards.domain.models.RewardDomainModel.Companion.REWARD_ID_3
import com.gb.opaltest.features.rewards.domain.models.RewardDomainModel.Companion.REWARD_ID_4
import com.gb.opaltest.features.rewards.domain.models.RewardDomainModel.Companion.REWARD_ID_5
import kotlinx.collections.immutable.toPersistentList
import java.text.DateFormat
import java.util.Locale
import com.gb.opaltest.core.design.R.drawable as drawables
import com.gb.opaltest.core.translations.R.string as translations

fun HomeDataDomainModel.toHomeRewardUiModel(
    onSettingsClicked: () -> Unit,
    onClaimedRewardClicked: (String) -> Unit,
    shouldShowSettingsBottomSheet: Boolean,
    onSettingsBottomSheetClosed: () -> Unit,
    onSettingsBottomSheetButtonClicked: (Int) -> Unit,
    onAddFriendButtonClicked: (String) -> Unit,
    onShareLinkButtonClicked: (String) -> Unit,
): HomeViewStateUiModel {
    val settingsBottomSheetViewState = getSettingsBottomSheetViewState(
        shouldShowSettingsBottomSheet = shouldShowSettingsBottomSheet,
        referredUsersCount = this.referredUsers.size,
        onClosed = onSettingsBottomSheetClosed,
        onButtonClicked = onSettingsBottomSheetButtonClicked,
    )
    val currentReward = getCurrentReward(
        rewards = this.rewards,
        onClaimedRewardClicked = onClaimedRewardClicked,
    )

    val dateFormatter: DateFormat =
        DateFormat.getDateInstance(
            DateFormat.SHORT,
            Locale.getDefault(),
        )

    return HomeViewStateUiModel(
        referralCode = "X3FRR",
        currentReward = currentReward,
        referredUsers = this.referredUsers.map { it.toUiModel(dateFormatter) }.toPersistentList(),
        onSettingsClicked = onSettingsClicked,
        rewards = this.rewards.map {
            it.toHomeRewardUiModel(onClaimedRewardClicked = onClaimedRewardClicked)
        }.toPersistentList(),
        settingsBottomSheetViewState = settingsBottomSheetViewState,
        onAddFriendButtonClicked = onAddFriendButtonClicked,
        onShareLinkButtonClicked = onShareLinkButtonClicked,
    )
}

private fun getCurrentReward(
    rewards: List<HomeRewardDomainModel>,
    onClaimedRewardClicked: (String) -> Unit,
): HomeCurrentRewardUiModel {
    val rewardToClaim =
        rewards.firstOrNull { it.state == HomeRewardDomainModel.HomeRewardStateDomainModel.Unclaimed }
    val rewardInProgress =
        rewards.firstOrNull { it.state is HomeRewardDomainModel.HomeRewardStateDomainModel.InProgress }
    return when {
        rewardToClaim != null -> {
            HomeCurrentRewardUiModel.Visible.UnClaimed(
                imageDrawableResId = getGemDrawable(rewardToClaim.id),
                title = TextUiModel.StringRes(getGemTitle(rewardToClaim.id)),
                subtitle = TextUiModel.StringRes(translations.referral_next_unlock),
                id = rewardToClaim.id,
                onClick = onClaimedRewardClicked,
            )
        }

        rewardInProgress != null -> {
            HomeCurrentRewardUiModel.Visible.InProgress(
                imageDrawableResId = getGemDrawable(rewardInProgress.id),
                title = TextUiModel.StringRes(getGemTitle(rewardInProgress.id)),
                subtitle = TextUiModel.StringRes(translations.referral_next_reward),
                progress = (rewardInProgress.state as HomeRewardDomainModel.HomeRewardStateDomainModel.InProgress).progress,
                total = (rewardInProgress.state as HomeRewardDomainModel.HomeRewardStateDomainModel.InProgress).total,
            )
        }

        else -> {
            HomeCurrentRewardUiModel.Hidden
        }
    }
}

private fun ReferredUserDomainModel.toUiModel(dataFormat: DateFormat): HomeReferredUserUiModel {
    val formatedDate = dataFormat.format(this.date)
    return HomeReferredUserUiModel(
        id = this.id,
        name = this.name,
        signUpdate = TextUiModel.StringRes(
            translations.referral_referee_signup_date,
            arrayOf(formatedDate)
        ),
    )
}

private fun HomeRewardDomainModel.toHomeRewardUiModel(
    onClaimedRewardClicked: (String) -> Unit,
): HomeRewardUiModel {
    val drawableResId = getGemDrawable(this.id)
    val title = getGemTitle(this.id)
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
        threshold = TextUiModel.PluralRes(
            plurals.reward_threshold_count,
            this.threshold,
            arrayOf(this.threshold)
        ),
        title = TextUiModel.StringRes(title),
        subtitle = TextUiModel.StringRes(subtitle),
        footer = when (val state = this.state) {
            is HomeRewardDomainModel.HomeRewardStateDomainModel.InProgress -> HomeRewardFooterUiModel.InProgress(
                progress = state.progress / state.total.toFloat(),
            )

            is HomeRewardDomainModel.HomeRewardStateDomainModel.Claimed -> HomeRewardFooterUiModel.Unlocked.Claimed
            else -> HomeRewardFooterUiModel.Unlocked.UnClaimed(onClick = onClaimedRewardClicked)
        },
    )
}

@DrawableRes
private fun getGemDrawable(id: String): Int =
    when (id) {
        REWARD_ID_1 -> drawables.reward_1
        REWARD_ID_2 -> drawables.reward_2
        REWARD_ID_3 -> drawables.reward_3
        REWARD_ID_4 -> drawables.reward_4
        REWARD_ID_5 -> drawables.reward_5
        else -> drawables.reward_6
    }

@StringRes
private fun getGemTitle(id: String): Int =
    when (id) {
        REWARD_ID_1 -> translations.reward_1_title
        REWARD_ID_2 -> translations.reward_2_title
        REWARD_ID_3 -> translations.reward_3_title
        REWARD_ID_4 -> translations.reward_4_title
        REWARD_ID_5 -> translations.reward_5_title
        else -> translations.reward_6_title
    }

fun getSettingsBottomSheetViewState(
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
