package com.gb.opaltest.core.design

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import timber.log.Timber

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

fun createAppThemeData(
    context: Context,
    @DrawableRes gemDrawableResId: Int,
    gemId: String
): AppThemeData? {
    val bitmap = AppCompatResources.getDrawable(context, gemDrawableResId)?.toBitmap()
    if (bitmap == null) {
        Timber.e("createAppThemeData: bitmap is null for drawableResId=$gemDrawableResId")
        return null
    }
    val palette = Palette.from(bitmap).generate()
    val (mainColor, secondaryColor) = if (gemId == "b1") {
        // for first gem, using colors from screenshot test web page to match at best
        Colors.LightGreen to Colors.LightBlue
    } else {
        (palette.vibrantSwatch?.let { Color(it.rgb) } ?: Colors.Black) to
                (palette.lightVibrantSwatch?.let { Color(it.rgb) } ?: Colors.Black)
    }
    return AppThemeData(
        ambientBackgroundGemDrawableResId = gemDrawableResId,
        mainColor = mainColor,
        secondaryColor = secondaryColor,
    )
}
