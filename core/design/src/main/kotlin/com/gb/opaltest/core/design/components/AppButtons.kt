package com.gb.opaltest.core.design.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
fun AppButton(
    modifier: Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        LocalAppThemeData.current.mainColor,
                        LocalAppThemeData.current.secondaryColor,
                    )
                ),
                shape = RoundedCornerShape(30.dp),
            ),
        shape = RoundedCornerShape(30.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Colors.Transparent
        ),
        content = content,
        onClick = {

        })
}
