package com.gb.opaltest.features.home.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gb.opaltest.core.design.Body
import com.gb.opaltest.core.design.Colors
import com.gb.opaltest.core.design.TextSize
import com.gb.opaltest.core.design.TextWeight
import com.gb.opaltest.core.design.components.AppBottomSheet
import com.gb.opaltest.features.home.presentation.models.HomePickGemBottomSheetViewState
import kotlinx.coroutines.launch
import com.gb.opaltest.core.design.R.drawable as drawables
import com.gb.opaltest.core.translations.R.string as translations

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePickGemBottomSheet(
    viewState: HomePickGemBottomSheetViewState,
) {

    val scope = rememberCoroutineScope()

    val state = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    if (viewState is HomePickGemBottomSheetViewState.Visible) {
        AppBottomSheet(
            state = state,
            onBottomSheetClosed = viewState.onClosed,
            content = {
                var selectedGemId by remember {
                    mutableStateOf(viewState.selectedGemId)
                }

                val gridState: LazyGridState = rememberLazyGridState()
                val requiredCellSize =
                    (LocalConfiguration.current.screenWidthDp.toFloat() / 2f) - 24.dp.value

                Row(
                    modifier = Modifier
                        .padding(start = 24.dp, top = 8.dp, end = 8.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Body(
                        modifier = Modifier.weight(1f),
                        text = stringResource(translations.home_pick_gem_bottom_sheet_title),
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

                LazyVerticalGrid(
                    modifier = Modifier
                        .navigationBarsPadding()
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp),
                    columns = GridCells.Adaptive(requiredCellSize.dp),
                    state = gridState
                ) {
                    items(items = viewState.gems, key = { it.id }) { gem ->
                        Image(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth()
                                .clip(
                                    RoundedCornerShape(16.dp)
                                ).border(
                                    width = if (gem.id == selectedGemId) 4.dp else 0.dp,
                                    color = Colors.White,
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .clickable {
                                    selectedGemId = gem.id
                                    scope
                                        .launch { state.hide() }
                                        .invokeOnCompletion {
                                            if (!state.isVisible) {
                                                viewState.onButtonClicked(selectedGemId)
                                            }
                                        }
                                },
                            painter = painterResource(gem.drawableResId),
                            contentDescription = "gem image",
                        )
                    }
                }
            }
        )
    }
}