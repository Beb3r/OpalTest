package com.gb.opaltest.features.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gb.opaltest.core.common.combines
import com.gb.opaltest.core.common.dispatchers.AppCoroutineDispatchers
import com.gb.opaltest.core.common.stateIn
import com.gb.opaltest.core.translations.TextUiModel
import com.gb.opaltest.features.gems.domain.use_cases.ClearCurrentGemUseCase
import com.gb.opaltest.features.gems.domain.use_cases.ObserveCurrentGemUseCase
import com.gb.opaltest.features.gems.domain.use_cases.ObserveGemsUseCase
import com.gb.opaltest.features.gems.domain.use_cases.SetCurrentGemUseCase
import com.gb.opaltest.features.home.domain.use_cases.ObserveHomeDataUseCase
import com.gb.opaltest.features.home.presentation.mappers.toHomeRewardUiModel
import com.gb.opaltest.features.home.presentation.models.HomeEventsUiModel
import com.gb.opaltest.features.home.presentation.models.HomeRewardBenefitsUiModel
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
import java.text.DateFormat
import java.util.Locale
import com.gb.opaltest.core.translations.R.string as translations

@KoinViewModel
class HomeViewModel(
    observeHomeDataUseCase: ObserveHomeDataUseCase,
    observeGemsUseCase: ObserveGemsUseCase,
    observeCurrentGemUseCase: ObserveCurrentGemUseCase,
    private val appCoroutineDispatchers: AppCoroutineDispatchers,
    private val setReferredUseCase: SetReferredUsersUseCase,
    private val setClaimedRewardIdUseCase: SetClaimedRewardIdUseCase,
    private val clearClaimedRewardsUseCase: ClearClaimedRewardsUseCase,
    private val clearReferredUsersUseCase: ClearReferredUsersUseCase,
    private val setCurrentGemUseCase: SetCurrentGemUseCase,
    private val clearCurrentGemUseCase: ClearCurrentGemUseCase,
) : ViewModel() {

    private val shouldShowSimulateReferralsBottomSheetFlow = MutableStateFlow(false)
    private val shouldShowPickGemBottomSheetFlow = MutableStateFlow(false)
    private val rewardBenefitsDataFlow = MutableStateFlow<HomeRewardBenefitsUiModel?>(null)

    private val _eventsFlow = MutableSharedFlow<HomeEventsUiModel>()
    val eventsFlow = _eventsFlow.asSharedFlow()

    private val dateFormat by lazy {
        DateFormat.getDateInstance(
            DateFormat.SHORT,
            Locale.getDefault(),
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val viewState = combines(
        observeHomeDataUseCase(),
        observeGemsUseCase(),
        observeCurrentGemUseCase(),
        shouldShowSimulateReferralsBottomSheetFlow,
        shouldShowPickGemBottomSheetFlow,
        rewardBenefitsDataFlow,
    ).mapLatest { (homeData, gems, currentGem, shouldShowSimulateReferralsBottomSheet, shouldShowPickGemBottomSheet, rewardBenefitsData) ->
        homeData.toHomeRewardUiModel(
            gems = gems,
            currentGem = currentGem,
            onClaimedRewardClicked = ::onClaimRewardClicked,
            shouldShowSimulateReferralsBottomSheet = shouldShowSimulateReferralsBottomSheet,
            onSimulateReferralsBottomSheetClosed = ::onSimulateReferralsBottomSheetClosed,
            onSimulateReferralsBottomSheetButtonClicked = ::onSimulateReferralsBottomSheetButtonClicked,
            shouldShowPickGemBottomSheet = shouldShowPickGemBottomSheet,
            onPickGemBottomSheetClosed = ::onPickGemBottomSheetClosed,
            onPickGemBottomSheetButtonClicked = ::onPickGemBottomSheetButtonClicked,
            onAddFriendButtonClicked = ::onAddFriendButtonClicked,
            onShareLinkButtonClicked = ::onShareLinkButtonClicked,
            onSettingsSimulateReferralsClicked = ::onSettingsSimulateReferralsClicked,
            onSettingsPickGemClicked = ::onSettingsPickGemClicked,
            onSettingsClearClicked = ::onSettingsClearClicked,
            dateFormat = dateFormat,
            rewardBenefitsData = rewardBenefitsData,
            onRewardBenefitsDialogClosed = ::onRewardBenefitsDialogClosed,
            onRewardBenefitsDialogGemButtonClicked = ::onRewardBenefitsDialogGemButtonClicked
        )
    }.stateIn(
        scope = viewModelScope,
        initialValue = HomeViewStateUiModel.DEFAULT,
    )

    private fun onSettingsSimulateReferralsClicked() {
        viewModelScope.launch {
            shouldShowSimulateReferralsBottomSheetFlow.value = true
        }
    }

    private fun onSettingsPickGemClicked() {
        viewModelScope.launch {
            shouldShowPickGemBottomSheetFlow.value = true
        }
    }

    private fun onSettingsClearClicked() {
        viewModelScope.launch(appCoroutineDispatchers.io) {
            clearClaimedRewardsUseCase()
            clearReferredUsersUseCase()
            clearCurrentGemUseCase()
        }
    }

    private fun onSimulateReferralsBottomSheetClosed() {
        viewModelScope.launch {
            shouldShowSimulateReferralsBottomSheetFlow.value = false
        }
    }

    private fun onSimulateReferralsBottomSheetButtonClicked(referredUsersCount: Int) {
        shouldShowSimulateReferralsBottomSheetFlow.value = false
        viewModelScope.launch(appCoroutineDispatchers.io) {
            delay(1000)
            setReferredUseCase(count = referredUsersCount)
        }
    }

    private fun onPickGemBottomSheetClosed() {
        viewModelScope.launch {
            shouldShowPickGemBottomSheetFlow.value = false
        }
    }

    private fun onPickGemBottomSheetButtonClicked(id: String) {
        shouldShowPickGemBottomSheetFlow.value = false
        viewModelScope.launch(appCoroutineDispatchers.io) {
            setCurrentGemUseCase(id = id)
        }
    }

    private fun onClaimRewardClicked(benefits: HomeRewardBenefitsUiModel) {
        viewModelScope.launch(appCoroutineDispatchers.io) {
            rewardBenefitsDataFlow.value = benefits
        }
    }

    private fun onRewardBenefitsDialogClosed(rewardId: String) {
        viewModelScope.launch {
            setClaimedRewardIdUseCase(id = rewardId)
            rewardBenefitsDataFlow.value = null
        }
    }

    private fun onRewardBenefitsDialogGemButtonClicked(rewardId: String, gemId: String) {
        viewModelScope.launch(appCoroutineDispatchers.io) {
            setClaimedRewardIdUseCase(id = rewardId)
            rewardBenefitsDataFlow.value = null
            setCurrentGemUseCase(id = gemId)
        }
    }

    private fun onAddFriendButtonClicked(referralCode: String) {
        emitShareLinkEvent(referralCode = referralCode)
    }

    private fun onShareLinkButtonClicked(referralCode: String) {
        emitShareLinkEvent(referralCode = referralCode)
    }

    private fun emitShareLinkEvent(referralCode: String) {
        val message = TextUiModel.StringRes(
            resId = translations.home_referral_share_link_message,
            formatArgs = arrayOf(referralCode)
        )
        viewModelScope.launch {
            _eventsFlow.emit(HomeEventsUiModel.ShareLink(message = message))
        }
    }
}
