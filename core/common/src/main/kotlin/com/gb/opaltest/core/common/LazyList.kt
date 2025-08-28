package com.gb.opaltest.core.common

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun LazyColumnScrollObserver(
    listState: LazyListState,
    onScrollOffsetChanged: (Int) -> Unit = {}
) {
    val firstVisibleItemIndex by remember { derivedStateOf { listState.firstVisibleItemIndex } }
    val scrollOffset by remember { derivedStateOf { (listState.firstVisibleItemScrollOffset / 2f).toInt()} }
    var map: Map<Int, Int> by remember { mutableStateOf(emptyMap()) }
    map = map.toMutableMap().apply { put(firstVisibleItemIndex, scrollOffset) }
    val totalOffset by remember { derivedStateOf { map.entries.sumOf { it.value } } }
    onScrollOffsetChanged(totalOffset)
}