package com.gb.opaltest.features.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gb.opaltest.core.common.combines
import com.gb.opaltest.core.common.dispatchers.AppCoroutineDispatchers
import com.gb.opaltest.core.common.stateIn
import com.gb.opaltest.features.home.domain.use_cases.ObserveHomeDataUseCase
import com.gb.opaltest.features.home.presentation.mappers.toHomeRewardUiModel
import com.gb.opaltest.features.home.presentation.models.HomeViewStateUiModel
import com.gb.opaltest.features.referral.domain.use_cases.SetReferredUsersUseCase
import com.gb.opaltest.features.rewards.domain.use_cases.SetClaimedRewardIdUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class HomeViewModel(
    observeHomeDataUseCase: ObserveHomeDataUseCase,
    private val appCoroutineDispatchers: AppCoroutineDispatchers,
    private val setReferredUseCase: SetReferredUsersUseCase,
    private val setClaimedRewardIdUseCase: SetClaimedRewardIdUseCase,
) : ViewModel() {

    private val shouldShowSettingsBottomSheetFlow = MutableStateFlow(false)

    @OptIn(ExperimentalCoroutinesApi::class)
    val viewState = combines(
        observeHomeDataUseCase(),
        shouldShowSettingsBottomSheetFlow,
    ).mapLatest { (homeData, shouldShowSettingsBottomSheet) ->
        homeData.toHomeRewardUiModel(
            onSettingsClicked = ::onSettingsClicked,
            onClaimedRewardClicked = ::onClaimRewardClicked,
            shouldShowSettingsBottomSheet = shouldShowSettingsBottomSheet,
            onSettingsBottomSheetClosed = ::onSettingsBottomSheetClosed,
            onSettingsBottomSheetButtonClicked = ::onSettingsBottomSheetButtonClicked,
        )
    }.stateIn(
        scope = viewModelScope,
        initialValue = HomeViewStateUiModel.DEFAULT,
    )

    private fun onSettingsClicked() {
        viewModelScope.launch {
            shouldShowSettingsBottomSheetFlow.value = true
        }
    }

    private fun onSettingsBottomSheetClosed() {
        shouldShowSettingsBottomSheetFlow.value = false
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
}
