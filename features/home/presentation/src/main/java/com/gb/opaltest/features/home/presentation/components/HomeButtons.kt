package com.gb.opaltest.features.home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gb.opaltest.core.design.Body
import com.gb.opaltest.core.design.Colors
import com.gb.opaltest.core.design.LocalAppThemeData
import com.gb.opaltest.core.design.TextSize
import com.gb.opaltest.core.design.TextWeight
import com.gb.opaltest.core.design.R.drawable as drawables
import com.gb.opaltest.core.translations.R.string as translations

@Composable
fun HomeAddFriendButton(
    onClick: () -> Unit,
) {
    val localAppThemeData = LocalAppThemeData.current

    Button(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
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
        content = {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(drawables.ic_add_friend),
                contentDescription = null,
                tint = Colors.Black,
            )

            Spacer(modifier = Modifier.width(8.dp))

            Body(
                text = stringResource(translations.referral_button_add_friend),
                textAlign = TextAlign.Center,
                textWeight = TextWeight.SEMI_BOLD,
                textSize = TextSize.LARGE,
                textColor = Colors.Black,
            )
        },
        onClick = onClick,
    )
}

@Composable
fun HomeShareButton(
    onClick: () -> Unit,
) {
    Button(
        modifier = Modifier
            .padding(top = 8.dp, bottom = 48.dp)
            .fillMaxWidth()
            .background(color = Colors.White, shape = RoundedCornerShape(30.dp)),
        shape = RoundedCornerShape(30.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Colors.Transparent,
        ),
        content = {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(drawables.ic_share),
                contentDescription = null,
                tint = Colors.Black,
            )

            Spacer(modifier = Modifier.width(8.dp))

            Body(
                text = stringResource(translations.referral_button_share_link),
                textAlign = TextAlign.Center,
                textWeight = TextWeight.SEMI_BOLD,
                textSize = TextSize.LARGE,
                textColor = Colors.Black,
            )
        },
        onClick = onClick,
    )
}
