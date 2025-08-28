package com.gb.opaltest.core.design.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.gb.opaltest.core.design.Colors
import com.gb.opaltest.core.design.LocalAppThemeData

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit,
) {
    val localAppThemeData = LocalAppThemeData.current

    Button(
        modifier = modifier
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        localAppThemeData.mainColor,
                        localAppThemeData.secondaryColor,
                    )
                ),
                shape = RoundedCornerShape(30.dp),
            ),
        shape = RoundedCornerShape(30.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Colors.Transparent,
        ),
        contentPadding = contentPadding,
        content = content,
        onClick = onClick,
    )
}

@Composable
fun SecondaryButton(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        modifier = modifier
            .background(color = Colors.White, shape = RoundedCornerShape(30.dp)),
        shape = RoundedCornerShape(30.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Colors.Transparent,
        ),
        contentPadding = contentPadding,
        content = content,
        onClick = onClick,
    )
}

@Composable
fun TertiaryButton(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        modifier = modifier
            .background(color = Colors.DarkGrey, shape = RoundedCornerShape(30.dp)),
        shape = RoundedCornerShape(30.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Colors.Transparent,
        ),
        contentPadding = contentPadding,
        content = content,
        onClick = onClick,
    )
}
