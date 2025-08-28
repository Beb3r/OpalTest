package com.gb.opaltest.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gb.opaltest.features.gems.domain.use_cases.ObserveCurrentGemUseCase
import com.gb.opaltest.features.gems.presentation.mappers.toGemUiModel
import com.gb.opaltest.features.gems.presentation.models.GemUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class MainAppViewModel(
    observeCurrentGemUseCase: ObserveCurrentGemUseCase,
) : ViewModel() {

    val currentGemFlow: Flow<GemUiModel> = observeCurrentGemUseCase()
        .map { it.toGemUiModel() }
        .shareIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
        )
}
