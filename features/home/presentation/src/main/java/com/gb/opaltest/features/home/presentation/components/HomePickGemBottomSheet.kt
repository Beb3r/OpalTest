package com.gb.opaltest.features.home.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import com.gb.opaltest.core.design.Body
import com.gb.opaltest.core.design.Colors
import com.gb.opaltest.core.design.TextSize
import com.gb.opaltest.core.design.TextWeight
import com.gb.opaltest.core.design.components.AppBottomSheet
import com.gb.opaltest.features.gems.presentation.models.GemUiModel
import com.gb.opaltest.features.home.presentation.models.HomePickGemBottomSheetViewState
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
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
                HomePickGemBottomSheetContent(
                    gems = viewState.gems,
                    selectedGemId = viewState.selectedGemId,
                    onClosedClicked = {
                        scope
                            .launch { state.hide() }
                            .invokeOnCompletion {
                                if (!state.isVisible) {
                                    viewState.onClosed()
                                }
                            }
                    },
                    onGemSelected = { selectedGemId ->
                        scope
                            .launch { state.hide() }
                            .invokeOnCompletion {
                                if (!state.isVisible) {
                                    viewState.onButtonClicked(selectedGemId)
                                }
                            }
                    }
                )
            }
        )
    }
}

@Composable
fun HomePickGemBottomSheetContent(
    gems: PersistentList<GemUiModel>,
    selectedGemId: String,
    onClosedClicked: () -> Unit,
    onGemSelected: (String) -> Unit,
) {
    var currentSelectedGemId by remember {
        mutableStateOf(selectedGemId)
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
            onClick = onClosedClicked,
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
        items(items = gems, key = { it.id }) { gem ->
            Image(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(16.dp)
                    )
                    .border(
                        width = if (gem.id == currentSelectedGemId) 4.dp else 0.dp,
                        color = Colors.White,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clickable {
                        currentSelectedGemId = gem.id
                        onGemSelected(gem.id)
                    },
                painter = painterResource(gem.drawableResId),
                contentDescription = "gem image",
            )
        }
    }
}

@PreviewScreenSizes
@Composable
fun HomePickGemBottomSheetContentPreview() {
    HomePickGemBottomSheetContent(
        gems = persistentListOf(
            GemUiModel(
                id = "gem2",
                drawableResId = drawables.gem_b1,
            ),
            GemUiModel(
                id = "gem3",
                drawableResId = drawables.gem_c1,
            ),
            GemUiModel(
                id = "gem4",
                drawableResId = drawables.gem_d1,
            ),
            GemUiModel(
                id = "gem5",
                drawableResId = drawables.gem_e1,
            ),
            GemUiModel(
                id = "gem6",
                drawableResId = drawables.gem_f1,
            ),
            GemUiModel(
                id = "gem7",
                drawableResId = drawables.gem_g1,
            ),
            GemUiModel(
                id = "gem8",
                drawableResId = drawables.gem_h1,
            ),
            GemUiModel(
                id = "gem9",
                drawableResId = drawables.gem_j1,
            ),
            GemUiModel(
                id = "gem10",
                drawableResId = drawables.gem_k1,
            ),
            GemUiModel(
                id = "gem11",
                drawableResId = drawables.gem_l1,
            ),
            GemUiModel(
                id = "gem12",
                drawableResId = drawables.gem_m1,
            ),
            GemUiModel(
                id = "gem13",
                drawableResId = drawables.gem_n1,
            ),
        ),
        selectedGemId = "gem4",
        onClosedClicked = {},
        onGemSelected = {}
    )

}