package com.gb.opaltest.core.design

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp


@Composable
fun Headline(
    text: String,
    modifier: Modifier = Modifier,
    textColor: Color = Colors.White,
    textAlign: TextAlign = TextAlign.Start,
    textWeight: TextWeight = TextWeight.REGULAR,
    textSize: TextSize = TextSize.MEDIUM,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        modifier = modifier,
        text = text,
        textAlign = textAlign,
        style = TextStyle(
            color = textColor,
            fontFamily = when (textWeight) {
                TextWeight.BOLD -> FontFamily(Font(R.font.inter_bold))
                TextWeight.SEMI_BOLD -> FontFamily(Font(R.font.inter_semibold))
                TextWeight.MEDIUM -> FontFamily(Font(R.font.inter_medium))
                TextWeight.REGULAR -> FontFamily(Font(R.font.inter_regular))
            },
            fontSize = when (textSize) {
                TextSize.LARGE -> 32.sp
                TextSize.MEDIUM -> 28.sp
                TextSize.SMALL -> 24.sp
            },
        ),
        maxLines = maxLines,
        overflow = overflow,
    )
}

@Composable
fun Title(
    text: String,
    modifier: Modifier = Modifier,
    textColor: Color = Colors.White,
    textAlign: TextAlign = TextAlign.Start,
    textWeight: TextWeight = TextWeight.REGULAR,
    textSize: TextSize = TextSize.MEDIUM,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        modifier = modifier,
        text = text,
        textAlign = textAlign,
        style = TextStyle(
            color = textColor,
            fontFamily = when (textWeight) {
                TextWeight.BOLD -> FontFamily(Font(R.font.inter_bold))
                TextWeight.SEMI_BOLD -> FontFamily(Font(R.font.inter_semibold))
                TextWeight.MEDIUM -> FontFamily(Font(R.font.inter_medium))
                TextWeight.REGULAR -> FontFamily(Font(R.font.inter_regular))
            },
            fontSize = when (textSize) {
                TextSize.LARGE -> 18.sp
                TextSize.MEDIUM -> 16.sp
                TextSize.SMALL -> 14.sp
            },
        ),
        maxLines = maxLines,
        overflow = overflow,
    )
}

@Composable
fun Body(
    text: String,
    modifier: Modifier = Modifier,
    textColor: Color = Colors.Black,
    textAlign: TextAlign = TextAlign.Start,
    textWeight: TextWeight = TextWeight.REGULAR,
    textSize: TextSize = TextSize.MEDIUM,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        modifier = modifier,
        text = text,
        textAlign = textAlign,
        style = TextStyle(
            color = textColor,
            fontFamily = when (textWeight) {
                TextWeight.BOLD -> FontFamily(Font(R.font.inter_bold))
                TextWeight.SEMI_BOLD -> FontFamily(Font(R.font.inter_semibold))
                TextWeight.MEDIUM -> FontFamily(Font(R.font.inter_medium))
                TextWeight.REGULAR -> FontFamily(Font(R.font.inter_regular))
            },
            fontSize = when (textSize) {
                TextSize.LARGE -> 16.sp
                TextSize.MEDIUM -> 14.sp
                TextSize.SMALL -> 12.sp
            },
        ),
        maxLines = maxLines,
        overflow = overflow,
    )
}

@Composable
fun Label(
    text: String,
    modifier: Modifier = Modifier,
    textColor: Color = Colors.White,
    textAlign: TextAlign = TextAlign.Start,
    textWeight: TextWeight = TextWeight.REGULAR,
    textSize: TextSize = TextSize.MEDIUM,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        modifier = modifier,
        text = text,
        textAlign = textAlign,
        style = TextStyle(
            color = textColor,
            fontFamily = when (textWeight) {
                TextWeight.BOLD -> FontFamily(Font(R.font.inter_bold))
                TextWeight.SEMI_BOLD -> FontFamily(Font(R.font.inter_semibold))
                TextWeight.MEDIUM -> FontFamily(Font(R.font.inter_medium))
                TextWeight.REGULAR -> FontFamily(Font(R.font.inter_regular))
            },
            fontSize = when (textSize) {
                TextSize.LARGE -> 14.sp
                TextSize.MEDIUM -> 12.sp
                TextSize.SMALL -> 11.sp
            },
        ),
        maxLines = maxLines,
        overflow = overflow,
    )
}

enum class TextWeight {
    BOLD,
    SEMI_BOLD,
    MEDIUM,
    REGULAR,
}

enum class TextSize {
    LARGE,
    MEDIUM,
    SMALL,
}
