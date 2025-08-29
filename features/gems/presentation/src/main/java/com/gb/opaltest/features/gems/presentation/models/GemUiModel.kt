package com.gb.opaltest.features.gems.presentation.models

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable

@Immutable
data class GemUiModel(
    val id: String,
    @DrawableRes
    val drawableResId: Int,
)
