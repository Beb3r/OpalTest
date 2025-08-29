package com.gb.opaltest.features.home.presentation.mappers

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.gb.opaltest.core.translations.R.plurals
import com.gb.opaltest.core.translations.TextUiModel
import com.gb.opaltest.features.gems.domain.models.GemDomainModel
import com.gb.opaltest.features.gems.presentation.mappers.getGemDrawable
import com.gb.opaltest.features.gems.presentation.mappers.toGemUiModel
import com.gb.opaltest.features.home.domain.models.HomeDataDomainModel
import com.gb.opaltest.features.home.domain.models.HomeRewardDomainModel
import com.gb.opaltest.features.home.domain.models.HomeRewardDomainModel.HomeRewardStateDomainModel
import com.gb.opaltest.features.home.presentation.models.HomeCurrentRewardUiModel
import com.gb.opaltest.features.home.presentation.models.HomePickGemBottomSheetViewState
import com.gb.opaltest.features.home.presentation.models.HomeReferredUserUiModel
import com.gb.opaltest.features.home.presentation.models.HomeRewardBenefitsDialogViewState
import com.gb.opaltest.features.home.presentation.models.HomeRewardBenefitsDialogViewState.Hidden
import com.gb.opaltest.features.home.presentation.models.HomeRewardBenefitsDialogViewState.Visible
import com.gb.opaltest.features.home.presentation.models.HomeRewardBenefitsUiModel
import com.gb.opaltest.features.home.presentation.models.HomeRewardFooterUiModel
import com.gb.opaltest.features.home.presentation.models.HomeRewardUiModel
import com.gb.opaltest.features.home.presentation.models.HomeSimulateReferralsBottomSheetViewState
import com.gb.opaltest.features.home.presentation.models.HomeViewStateUiModel
import com.gb.opaltest.features.referral.domain.models.ReferredUserDomainModel
import com.gb.opaltest.features.rewards.domain.models.RewardBenefitsDomainModel
import com.gb.opaltest.features.rewards.domain.models.RewardDomainModel.Companion.REWARD_ID_1
import com.gb.opaltest.features.rewards.domain.models.RewardDomainModel.Companion.REWARD_ID_2
import com.gb.opaltest.features.rewards.domain.models.RewardDomainModel.Companion.REWARD_ID_3
import com.gb.opaltest.features.rewards.domain.models.RewardDomainModel.Companion.REWARD_ID_4
import com.gb.opaltest.features.rewards.domain.models.RewardDomainModel.Companion.REWARD_ID_5
import kotlinx.collections.immutable.toPersistentList
import java.text.DateFormat
import com.gb.opaltest.core.design.R.drawable as drawables
import com.gb.opaltest.core.translations.R.string as translations

fun HomeDataDomainModel.toHomeRewardUiModel(
    gems: List<GemDomainModel>,
    currentGem: GemDomainModel,
    onSettingsSimulateReferralsClicked: () -> Unit,
    onClaimedRewardClicked: (HomeRewardBenefitsUiModel) -> Unit,
    shouldShowSimulateReferralsBottomSheet: Boolean,
    onSimulateReferralsBottomSheetClosed: () -> Unit,
    onSimulateReferralsBottomSheetButtonClicked: (Int) -> Unit,
    shouldShowPickGemBottomSheet: Boolean,
    onPickGemBottomSheetClosed: () -> Unit,
    onPickGemBottomSheetButtonClicked: (String) -> Unit,
    onAddFriendButtonClicked: (String) -> Unit,
    onShareLinkButtonClicked: (String) -> Unit,
    onSettingsPickGemClicked: () -> Unit,
    onSettingsClearClicked: () -> Unit,
    dateFormat: DateFormat,
    rewardBenefitsData: HomeRewardBenefitsUiModel?,
    onRewardBenefitsDialogClosed: (String) -> Unit,
    onRewardBenefitsDialogGemButtonClicked: (String, String) -> Unit,
): HomeViewStateUiModel {
    val currentReward = getCurrentReward(
        rewards = this.rewards,
        onClaimedRewardClicked = onClaimedRewardClicked,
    )

    val simulateReferralsBottomSheetViewState = getSimulateReferralsBottomSheetViewState(
        shouldShowSettingsBottomSheet = shouldShowSimulateReferralsBottomSheet,
        referredUsersCount = this.referredUsers.size,
        onClosed = onSimulateReferralsBottomSheetClosed,
        onButtonClicked = onSimulateReferralsBottomSheetButtonClicked,
    )

    val pickGemBottomSheetViewState = getPickGemBottomSheetViewState(
        shouldShowPickGemBottomSheet = shouldShowPickGemBottomSheet,
        gems = gems,
        selectedGemId = currentGem.id,
        onClosed = onPickGemBottomSheetClosed,
        onButtonClicked = onPickGemBottomSheetButtonClicked,
    )

    val rewardBenefitsDialogViewState = getRewardBenefitsDialogViewState(
        rewardBenefitsData = rewardBenefitsData,
        onClosed = onRewardBenefitsDialogClosed,
        onGemButtonClicked = onRewardBenefitsDialogGemButtonClicked,
    )

    return HomeViewStateUiModel(
        referralCode = "X3FRR",
        onAddFriendButtonClicked = onAddFriendButtonClicked,
        onShareLinkButtonClicked = onShareLinkButtonClicked,
        currentReward = currentReward,
        referredUsers = this.referredUsers.map { it.toUiModel(dateFormat) }.toPersistentList(),
        rewards = this.rewards.map {
            it.toHomeRewardUiModel(onClaimedRewardClicked = onClaimedRewardClicked)
        }.toPersistentList(),
        onSettingsSimulateReferralsClicked = onSettingsSimulateReferralsClicked,
        onSettingsPickGemClicked = onSettingsPickGemClicked,
        onSettingsClearClicked = onSettingsClearClicked,
        simulateReferralsBottomSheetViewState = simulateReferralsBottomSheetViewState,
        pickGemBottomSheetViewState = pickGemBottomSheetViewState,
        rewardBenefitsDialogViewState = rewardBenefitsDialogViewState,
    )
}

private fun getCurrentReward(
    rewards: List<HomeRewardDomainModel>,
    onClaimedRewardClicked: (HomeRewardBenefitsUiModel) -> Unit,
): HomeCurrentRewardUiModel {
    val rewardToClaim =
        rewards.firstOrNull { it.state is HomeRewardStateDomainModel.Unclaimed }
    val rewardInProgress =
        rewards.firstOrNull { it.state is HomeRewardStateDomainModel.InProgress }
    return when {
        rewardToClaim != null -> {
            HomeCurrentRewardUiModel.Visible.UnClaimed(
                imageDrawableResId = getRewardDrawable(rewardToClaim.id),
                title = TextUiModel.StringRes(getRewardTitle(rewardToClaim.id)),
                subtitle = TextUiModel.StringRes(translations.home_referral_next_unlock),
                benefits = (rewardToClaim.state as HomeRewardStateDomainModel.Unclaimed).benefits.toUiModel(rewardToClaim.id),
                onClick = onClaimedRewardClicked,
            )
        }

        rewardInProgress != null -> {
            HomeCurrentRewardUiModel.Visible.InProgress(
                imageDrawableResId = getRewardDrawable(rewardInProgress.id),
                title = TextUiModel.StringRes(getRewardTitle(rewardInProgress.id)),
                subtitle = TextUiModel.StringRes(translations.home_referral_next_reward),
                progress = (rewardInProgress.state as HomeRewardStateDomainModel.InProgress).progress,
                total = (rewardInProgress.state as HomeRewardStateDomainModel.InProgress).total,
            )
        }

        else -> {
            HomeCurrentRewardUiModel.Hidden
        }
    }
}

private fun ReferredUserDomainModel.toUiModel(dateFormat: DateFormat): HomeReferredUserUiModel {
    val formatedDate = dateFormat.format(this.date)
    return HomeReferredUserUiModel(
        id = this.id,
        name = this.name,
        signUpdate = TextUiModel.StringRes(
            translations.home_referral_referred_user_signup_date,
            arrayOf(formatedDate)
        ),
    )
}

private fun HomeRewardDomainModel.toHomeRewardUiModel(
    onClaimedRewardClicked: (HomeRewardBenefitsUiModel) -> Unit,
): HomeRewardUiModel {
    val drawableResId = getRewardDrawable(this.id)
    val title = getRewardTitle(this.id)
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
            plurals.home_reward_threshold_count,
            this.threshold,
            arrayOf(this.threshold)
        ),
        title = TextUiModel.StringRes(title),
        subtitle = TextUiModel.StringRes(subtitle),
        footer = when (val state = this.state) {
            is HomeRewardStateDomainModel.InProgress -> HomeRewardFooterUiModel.InProgress(
                progress = state.progress / state.total.toFloat(),
            )

            is HomeRewardStateDomainModel.Unclaimed -> HomeRewardFooterUiModel.Unlocked.UnClaimed(
                benefits = state.benefits.toUiModel(this.id),
                onClick = onClaimedRewardClicked,
            )

            else -> HomeRewardFooterUiModel.Unlocked.Claimed
        },
    )
}

@DrawableRes
private fun getRewardDrawable(id: String): Int =
    when (id) {
        REWARD_ID_1 -> drawables.reward_1
        REWARD_ID_2 -> drawables.reward_2
        REWARD_ID_3 -> drawables.reward_3
        REWARD_ID_4 -> drawables.reward_4
        REWARD_ID_5 -> drawables.reward_5
        else -> drawables.reward_6
    }

@StringRes
private fun getRewardTitle(id: String): Int =
    when (id) {
        REWARD_ID_1 -> translations.reward_1_title
        REWARD_ID_2 -> translations.reward_2_title
        REWARD_ID_3 -> translations.reward_3_title
        REWARD_ID_4 -> translations.reward_4_title
        REWARD_ID_5 -> translations.reward_5_title
        else -> translations.reward_6_title
    }

private fun getSimulateReferralsBottomSheetViewState(
    shouldShowSettingsBottomSheet: Boolean,
    referredUsersCount: Int,
    onClosed: () -> Unit,
    onButtonClicked: (Int) -> Unit
): HomeSimulateReferralsBottomSheetViewState {
    return if (shouldShowSettingsBottomSheet) {
        HomeSimulateReferralsBottomSheetViewState.Visible(
            referredUsersCount = referredUsersCount,
            onClosed = onClosed,
            onButtonClicked = onButtonClicked,
        )
    } else {
        HomeSimulateReferralsBottomSheetViewState.Hidden
    }
}

private fun getPickGemBottomSheetViewState(
    shouldShowPickGemBottomSheet: Boolean,
    gems: List<GemDomainModel>,
    selectedGemId: String,
    onClosed: () -> Unit,
    onButtonClicked: (String) -> Unit
): HomePickGemBottomSheetViewState {
    return if (shouldShowPickGemBottomSheet) {
        HomePickGemBottomSheetViewState.Visible(
            gems = gems.map { it.toGemUiModel() }.toPersistentList(),
            selectedGemId = selectedGemId,
            onClosed = onClosed,
            onButtonClicked = onButtonClicked,
        )
    } else {
        HomePickGemBottomSheetViewState.Hidden
    }
}

private fun RewardBenefitsDomainModel.toUiModel(rewardId: String): HomeRewardBenefitsUiModel =
    when (this) {
        is RewardBenefitsDomainModel.Gem -> HomeRewardBenefitsUiModel.Gem(rewardId = rewardId, gemId = this.gemId)
        RewardBenefitsDomainModel.FreeSubscription2years -> HomeRewardBenefitsUiModel.FreeSubscription2years(rewardId = rewardId)
        RewardBenefitsDomainModel.FreeSubscriptionLifeTime -> HomeRewardBenefitsUiModel.FreeSubscriptionLifeTime(rewardId = rewardId)
        RewardBenefitsDomainModel.Gift -> HomeRewardBenefitsUiModel.Gift(rewardId = rewardId)
    }

private fun getRewardBenefitsDialogViewState(
    rewardBenefitsData: HomeRewardBenefitsUiModel?,
    onClosed: (String) -> Unit,
    onGemButtonClicked: (String, String) -> Unit,
): HomeRewardBenefitsDialogViewState =
    when (rewardBenefitsData) {
        null -> Hidden
        is HomeRewardBenefitsUiModel.Gem -> Visible.Gem(
            rewardId = rewardBenefitsData.rewardId,
            gemId = rewardBenefitsData.gemId,
            rewardDrawableResId = getGemDrawable(rewardBenefitsData.gemId),
            onCloseButtonClicked = onClosed,
            onSetGemClicked = onGemButtonClicked,
        )

        is HomeRewardBenefitsUiModel.FreeSubscription2years -> Visible.FreeSubscription2years(
            rewardId = rewardBenefitsData.rewardId,
            onCloseButtonClicked = onClosed,
        )

        is HomeRewardBenefitsUiModel.FreeSubscriptionLifeTime -> Visible.FreeSubscriptionLifeTime(
            rewardId = rewardBenefitsData.rewardId,
            onCloseButtonClicked = onClosed,
        )

        is HomeRewardBenefitsUiModel.Gift -> Visible.Gift(
            rewardId = rewardBenefitsData.rewardId,
            onCloseButtonClicked = onClosed,
        )
    }

