package com.gb.opaltest.features.home.presentation.models

import androidx.compose.runtime.Immutable
import com.gb.opaltest.core.translations.TextUiModel
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

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
    val settingsBottomSheetViewState: HomeSettingsBottomSheetViewState,
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
            settingsBottomSheetViewState = HomeSettingsBottomSheetViewState.Hidden,
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
            val id: String,
            val onClick: (String) -> Unit
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
        data class UnClaimed(val onClick: (String) -> Unit) : Unlocked
    }

    data class InProgress(val progress: Float) : HomeRewardFooterUiModel
}

@Immutable
sealed interface HomeSettingsBottomSheetViewState {
    data object Hidden : HomeSettingsBottomSheetViewState
    data class Visible(
        val referredUsersCount: Int,
        val onClosed: () -> Unit,
        val onButtonClicked: (Int) -> Unit
    ) : HomeSettingsBottomSheetViewState
}

@Immutable
data class HomeReferredUserUiModel(
    val id: String,
    val name: String,
    val signUpdate: TextUiModel,
)
