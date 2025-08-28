package com.gb.opaltest.features.home.presentation.models

import androidx.compose.runtime.Immutable
import com.gb.opaltest.core.translations.TextUiModel
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class HomeViewStateUiModel(
    val referralCode: String,
    val referredUsersCount: String,
    //val currentReward:
    val rewards: PersistentList<HomeRewardUiModel>,
    val onSettingsClicked: () -> Unit,
    val settingsBottomSheetViewState: HomeSettingsBottomSheetViewState,
) {
    companion object {
        val DEFAULT = HomeViewStateUiModel(
            referralCode = "-----",
            referredUsersCount = "-",
            rewards = persistentListOf(),
            onSettingsClicked = { },
            settingsBottomSheetViewState = HomeSettingsBottomSheetViewState.Hidden,
        )
    }
}

/*@Immutable
data class CurrentRewardUiModel(
    val imageDrawableResId: Int,
    val title: TextUiModel,
    val subtitle: TextUiModel,
    val footer: CurrentRewardFooterUiModel,
)*/


@Immutable
data class HomeRewardUiModel(
    val id: String,
    val imageDrawableResId: Int,
    val friendsCount: TextUiModel,
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
