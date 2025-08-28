package com.gb.opaltest.features.home.presentation.models

import com.gb.opaltest.core.translations.TextUiModel

interface HomeEventsUiModel {

    data class ShareLink(val message: TextUiModel) : HomeEventsUiModel
}
