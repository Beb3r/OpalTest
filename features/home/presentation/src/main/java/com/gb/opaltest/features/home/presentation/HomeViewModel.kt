package com.gb.opaltest.features.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gb.opaltest.core.common.combines
import com.gb.opaltest.core.common.dispatchers.AppCoroutineDispatchers
import com.gb.opaltest.core.common.stateIn
import com.gb.opaltest.core.translations.TextUiModel
import com.gb.opaltest.features.home.domain.use_cases.ObserveHomeDataUseCase
import com.gb.opaltest.features.home.presentation.mappers.toHomeRewardUiModel
import com.gb.opaltest.features.home.presentation.models.HomeEventsUiModel
import com.gb.opaltest.features.home.presentation.models.HomeViewStateUiModel
import com.gb.opaltest.features.referral.domain.use_cases.ClearReferredUsersUseCase
import com.gb.opaltest.features.referral.domain.use_cases.SetReferredUsersUseCase
import com.gb.opaltest.features.rewards.domain.use_cases.ClearClaimedRewardsUseCase
import com.gb.opaltest.features.rewards.domain.use_cases.SetClaimedRewardIdUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import com.gb.opaltest.core.translations.R.string as translations

@KoinViewModel
class HomeViewModel(
    observeHomeDataUseCase: ObserveHomeDataUseCase,
    private val appCoroutineDispatchers: AppCoroutineDispatchers,
    private val setReferredUseCase: SetReferredUsersUseCase,
    private val setClaimedRewardIdUseCase: SetClaimedRewardIdUseCase,
    private val clearClaimedRewardsUseCase: ClearClaimedRewardsUseCase,
    private val clearReferredUsersUseCase: ClearReferredUsersUseCase,
) : ViewModel() {

    private val shouldShowSettingsBottomSheetFlow = MutableStateFlow(false)

    private val _eventsFlow = MutableSharedFlow<HomeEventsUiModel>()
    val eventsFlow = _eventsFlow.asSharedFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val viewState = combines(
        observeHomeDataUseCase(),
        shouldShowSettingsBottomSheetFlow,
    ).mapLatest { (homeData, shouldShowSettingsBottomSheet) ->
        homeData.toHomeRewardUiModel(
            onClaimedRewardClicked = ::onClaimRewardClicked,
            shouldShowSettingsBottomSheet = shouldShowSettingsBottomSheet,
            onSettingsBottomSheetClosed = ::onSettingsBottomSheetClosed,
            onSettingsBottomSheetButtonClicked = ::onSettingsBottomSheetButtonClicked,
            onAddFriendButtonClicked = ::onAddFriendButtonClicked,
            onShareLinkButtonClicked = ::onShareLinkButtonClicked,
            onSettingsSimulateReferralsClicked = ::onSettingsSimulateReferralsClicked,
            onSettingsPickGemClicked = ::onSettingsPickGemClicked,
            onSettingsClearClicked = ::onSettingsClearClicked,
        )
    }.stateIn(
        scope = viewModelScope,
        initialValue = HomeViewStateUiModel.DEFAULT,
    )

    private fun onSettingsSimulateReferralsClicked() {
        viewModelScope.launch {
            shouldShowSettingsBottomSheetFlow.value = true
        }
    }

    private fun onSettingsPickGemClicked() {
        viewModelScope.launch {
            shouldShowSettingsBottomSheetFlow.value = true
        }
    }

    private fun onSettingsClearClicked() {
        viewModelScope.launch(appCoroutineDispatchers.io) {
            clearClaimedRewardsUseCase()
            clearReferredUsersUseCase()
        }
    }

    private fun onSettingsBottomSheetClosed() {
        viewModelScope.launch {
            shouldShowSettingsBottomSheetFlow.value = false
        }
    }

    private fun onSettingsBottomSheetButtonClicked(referredUsersCount: Int) {
        shouldShowSettingsBottomSheetFlow.value = false
        viewModelScope.launch(appCoroutineDispatchers.io) {
            delay(1000)
            setReferredUseCase(count = referredUsersCount)
        }
    }

    private fun onClaimRewardClicked(rewardId: String) {
        viewModelScope.launch(appCoroutineDispatchers.io) {
            setClaimedRewardIdUseCase(id = rewardId)
        }
    }

    private fun onAddFriendButtonClicked(referralCode: String) {
        val message = TextUiModel.StringRes(
            resId = translations.home_referral_share_link_message,
            formatArgs = arrayOf(referralCode)
        )
        viewModelScope.launch {
            _eventsFlow.emit(HomeEventsUiModel.ShareLink(message = message))
        }
    }

    private fun onShareLinkButtonClicked(referralCode: String) {
        val message = TextUiModel.StringRes(
            resId = translations.home_referral_share_link_message,
            formatArgs = arrayOf(referralCode)
        )
        viewModelScope.launch {
            _eventsFlow.emit(HomeEventsUiModel.ShareLink(message = message))
        }
    }
}
