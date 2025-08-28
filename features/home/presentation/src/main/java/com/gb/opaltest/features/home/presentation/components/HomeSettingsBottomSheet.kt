package com.gb.opaltest.features.home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberSliderState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gb.opaltest.core.design.Body
import com.gb.opaltest.core.design.Colors
import com.gb.opaltest.core.design.TextSize
import com.gb.opaltest.core.design.TextWeight
import com.gb.opaltest.core.design.Title
import com.gb.opaltest.core.design.components.AppBottomSheet
import com.gb.opaltest.features.home.presentation.models.HomeSettingsBottomSheetViewState
import kotlinx.coroutines.launch
import com.gb.opaltest.core.design.R.drawable as drawables
import com.gb.opaltest.core.translations.R.string as translations

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeSettingsBottomSheet(
    viewState: HomeSettingsBottomSheetViewState,
) {

    val scope = rememberCoroutineScope()

    val state = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    if (viewState is HomeSettingsBottomSheetViewState.Visible) {
        AppBottomSheet(
            state = state,
            onBottomSheetClosed = viewState.onClosed,
            content = {
                val sliderState =
                    rememberSliderState(
                        value = viewState.referredUsersCount.toFloat(),
                        valueRange = 0f..100f,
                    )

                Row(
                    modifier = Modifier
                        .padding(start = 24.dp, top = 8.dp, end = 8.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Body(
                        modifier = Modifier.weight(1f),
                        text = stringResource(translations.home_settings_bottom_sheet_title),
                        textColor = Colors.White,
                        textWeight = TextWeight.BOLD,
                        textSize = TextSize.LARGE,
                    )
                    IconButton(
                        onClick = {
                            scope
                                .launch { state.hide() }
                                .invokeOnCompletion {
                                    if (!state.isVisible) {
                                        viewState.onClosed()
                                    }
                                }
                        },
                    ) {
                        Icon(
                            painter = painterResource(drawables.ic_close),
                            contentDescription = "",
                            tint = Colors.White
                        )
                    }
                }

                Title(
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                    text = sliderState.value.toInt().toString(),
                    textAlign = TextAlign.Center,
                    textWeight = TextWeight.MEDIUM,
                    textSize = TextSize.LARGE
                )

                Slider(
                    modifier = Modifier.padding(vertical = 24.dp, horizontal = 32.dp),
                    state = sliderState
                )

                Button(
                    modifier = Modifier
                        .safeContentPadding()
                        .fillMaxWidth()
                        .background(color = Colors.White, shape = RoundedCornerShape(30.dp)),
                    shape = RoundedCornerShape(30.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Colors.Transparent,
                    ),
                    content = {
                        Body(
                            text = stringResource(translations.home_settings_bottom_sheet_button),
                            textAlign = TextAlign.Center,
                            textWeight = TextWeight.SEMI_BOLD,
                            textSize = TextSize.LARGE,
                            textColor = Colors.Black,
                        )
                    },
                    onClick = {
                        scope
                            .launch { state.hide() }
                            .invokeOnCompletion {
                                if (!state.isVisible) {
                                    viewState.onButtonClicked(sliderState.value.toInt())
                                }
                            }
                    },
                )
            }
        )
    }
}
