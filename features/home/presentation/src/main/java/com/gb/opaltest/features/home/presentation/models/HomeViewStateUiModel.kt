package com.gb.opaltest.features.home.presentation.models

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import com.gb.opaltest.core.translations.TextUiModel
import com.gb.opaltest.features.gems.presentation.models.GemUiModel
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import com.gb.opaltest.core.design.R.drawable as drawables
import com.gb.opaltest.core.translations.R.string as translations

@Immutable
data class HomeViewStateUiModel(
    val referralCode: String,
    val onAddFriendButtonClicked: (String) -> Unit,
    val onShareLinkButtonClicked: (String) -> Unit,
    val currentReward: HomeCurrentRewardUiModel,
    val referredUsers: PersistentList<HomeReferredUserUiModel>,
    val rewards: PersistentList<HomeRewardUiModel>,
    val onSettingsSimulateReferralsClicked: () -> Unit,
    val onSettingsPickGemClicked: () -> Unit,
    val onSettingsClearClicked: () -> Unit,
    val simulateReferralsBottomSheetViewState: HomeSimulateReferralsBottomSheetViewState,
    val pickGemBottomSheetViewState: HomePickGemBottomSheetViewState,
    val rewardBenefitsDialogViewState: HomeRewardBenefitsDialogViewState,
) {
    companion object {
        val DEFAULT = HomeViewStateUiModel(
            referralCode = "-----",
            onAddFriendButtonClicked = { },
            onShareLinkButtonClicked = { },
            currentReward = HomeCurrentRewardUiModel.Hidden,
            referredUsers = persistentListOf(),
            rewards = persistentListOf(),
            onSettingsSimulateReferralsClicked = { },
            onSettingsPickGemClicked = { },
            onSettingsClearClicked = { },
            simulateReferralsBottomSheetViewState = HomeSimulateReferralsBottomSheetViewState.Hidden,
            pickGemBottomSheetViewState = HomePickGemBottomSheetViewState.Hidden,
            rewardBenefitsDialogViewState = HomeRewardBenefitsDialogViewState.Hidden,
        )
    }
}

@Immutable
sealed interface HomeCurrentRewardUiModel {
    data object Hidden : HomeCurrentRewardUiModel

    @Immutable
    sealed interface Visible : HomeCurrentRewardUiModel {
        val imageDrawableResId: Int
        val title: TextUiModel
        val subtitle: TextUiModel

        data class UnClaimed(
            override val imageDrawableResId: Int,
            override val title: TextUiModel,
            override val subtitle: TextUiModel,
            val benefits: HomeRewardBenefitsUiModel,
            val onClick: (HomeRewardBenefitsUiModel) -> Unit
        ) : Visible

        data class InProgress(
            override val imageDrawableResId: Int,
            override val title: TextUiModel,
            override val subtitle: TextUiModel,
            val progress: Int,
            val total: Int,
        ) : Visible
    }
}

@Immutable
data class HomeRewardUiModel(
    val id: String,
    val imageDrawableResId: Int,
    val threshold: TextUiModel,
    val title: TextUiModel,
    val subtitle: TextUiModel,
    val footer: HomeRewardFooterUiModel,
)

@Immutable
sealed interface HomeRewardFooterUiModel {
    sealed interface Unlocked : HomeRewardFooterUiModel {
        data object Claimed : Unlocked
        data class UnClaimed(
            val benefits: HomeRewardBenefitsUiModel,
            val onClick: (HomeRewardBenefitsUiModel) -> Unit
        ) : Unlocked
    }

    data class InProgress(val progress: Float) : HomeRewardFooterUiModel
}

@Immutable
sealed interface HomeSimulateReferralsBottomSheetViewState {
    data object Hidden : HomeSimulateReferralsBottomSheetViewState
    data class Visible(
        val referredUsersCount: Int,
        val onClosed: () -> Unit,
        val onButtonClicked: (Int) -> Unit
    ) : HomeSimulateReferralsBottomSheetViewState
}

@Immutable
sealed interface HomePickGemBottomSheetViewState {
    data object Hidden : HomePickGemBottomSheetViewState
    data class Visible(
        val gems: PersistentList<GemUiModel>,
        val selectedGemId: String,
        val onClosed: () -> Unit,
        val onButtonClicked: (String) -> Unit
    ) : HomePickGemBottomSheetViewState
}

@Immutable
data class HomeReferredUserUiModel(
    val id: String,
    val name: String,
    val signUpdate: TextUiModel,
)

@Immutable
sealed interface HomeRewardBenefitsUiModel {
    val rewardId: String

    data class Gem(override val rewardId: String, val gemId: String) : HomeRewardBenefitsUiModel
    data class FreeSubscription2years(override val rewardId: String) : HomeRewardBenefitsUiModel
    data class FreeSubscriptionLifeTime(override val rewardId: String) : HomeRewardBenefitsUiModel
    data class Gift(override val rewardId: String) : HomeRewardBenefitsUiModel
}

@Immutable
sealed interface HomeRewardBenefitsDialogViewState {
    data object Hidden : HomeRewardBenefitsDialogViewState
    sealed interface Visible : HomeRewardBenefitsDialogViewState {
        val rewardId: String
        val rewardDrawableResId: Int
        val title: TextUiModel
        val subtitle: TextUiModel
        val closeButtonText: TextUiModel
        val onCloseButtonClicked: (String) -> Unit

        data class Gem(
            override val rewardId: String,
            @DrawableRes
            override val rewardDrawableResId: Int,
            override val title: TextUiModel = TextUiModel.StringRes(translations.home_rewards_benefit_dialog_gem_title),
            override val subtitle: TextUiModel = TextUiModel.StringRes(translations.home_rewards_benefit_dialog_gem_subtitle),
            override val closeButtonText: TextUiModel = TextUiModel.StringRes(translations.home_rewards_benefit_dialog_gem_negative_button),
            override val onCloseButtonClicked: (String) -> Unit,
            val gemId: String,
            val onSetGemClicked: (String, String) -> Unit
        ) : Visible

        data class FreeSubscription2years(
            override val rewardId: String,
            @DrawableRes
            override val rewardDrawableResId: Int = drawables.reward_4,
            override val title: TextUiModel = TextUiModel.StringRes(translations.home_rewards_benefit_dialog_subscription_2_years_title),
            override val subtitle: TextUiModel = TextUiModel.StringRes(translations.home_rewards_benefit_dialog_subscription_subtitle),
            override val closeButtonText: TextUiModel = TextUiModel.StringRes(translations.home_rewards_benefit_dialog_subscription_button),
            override val onCloseButtonClicked: (String) -> Unit
        ) : Visible

        data class FreeSubscriptionLifeTime(
            override val rewardId: String,
            @DrawableRes
            override val rewardDrawableResId: Int = drawables.reward_5,
            override val title: TextUiModel = TextUiModel.StringRes(translations.home_rewards_benefit_dialog_subscription_lifetime_title),
            override val subtitle: TextUiModel = TextUiModel.StringRes(translations.home_rewards_benefit_dialog_subscription_subtitle),
            override val closeButtonText: TextUiModel = TextUiModel.StringRes(translations.home_rewards_benefit_dialog_subscription_button),
            override val onCloseButtonClicked: (String) -> Unit
        ) : Visible

        data class Gift(
            override val rewardId: String,
            @DrawableRes
            override val rewardDrawableResId: Int = drawables.reward_6,
            override val title: TextUiModel = TextUiModel.StringRes(translations.home_rewards_benefit_dialog_gift_title),
            override val subtitle: TextUiModel = TextUiModel.StringRes(translations.home_rewards_benefit_dialog_gift_subtitle),
            override val closeButtonText: TextUiModel = TextUiModel.StringRes(translations.home_rewards_benefit_dialog_gift_button),
            override val onCloseButtonClicked: (String) -> Unit
        ) : Visible
    }
}
