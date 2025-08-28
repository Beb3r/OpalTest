package com.gb.opaltest.core.design

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class AppThemeData(
    @DrawableRes
    val ambientBackgroundGemDrawableResId: Int = 0,
    val mainColor: Color = Colors.Black,
    val secondaryColor: Color = Colors.Black,
)

val LocalAppThemeData = staticCompositionLocalOf {
    AppThemeData()
}
