package com.gb.opaltest.core.design.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetDefaults
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.gb.opaltest.core.design.Colors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBottomSheet(
    state: SheetState,
    containerColor: Color = Colors.DarkGrey,
    dragHandle: @Composable (() -> Unit)? = { },
    content: @Composable ColumnScope.() -> Unit,
    onBottomSheetClosed: () -> Unit,
    sheetGesturesEnabled: Boolean = false,
    properties: ModalBottomSheetProperties = ModalBottomSheetDefaults.properties,
) {
    ModalBottomSheet(
        onDismissRequest = {
            onBottomSheetClosed()
        },
        sheetState = state,
        containerColor = containerColor,
        dragHandle = dragHandle,
        sheetGesturesEnabled = sheetGesturesEnabled,
        properties = properties,
        content = {
            content()
        },
    )
}