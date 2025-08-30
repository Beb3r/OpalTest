package com.gb.opaltest.features.home.presentation.components

import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gb.opaltest.core.design.Body
import com.gb.opaltest.core.design.Colors
import com.gb.opaltest.core.design.LocalAppThemeData
import com.gb.opaltest.core.design.TextSize
import com.gb.opaltest.core.design.TextWeight
import com.gb.opaltest.core.design.Title
import com.gb.opaltest.core.design.components.AmbientBackground
import com.gb.opaltest.core.design.components.PrimaryButton
import com.gb.opaltest.core.design.components.SecondaryButton
import com.gb.opaltest.core.design.createAppThemeData
import com.gb.opaltest.core.translations.toValue
import com.gb.opaltest.features.home.presentation.models.HomeRewardBenefitsDialogViewState
import com.gb.opaltest.features.home.presentation.models.HomeRewardBenefitsDialogViewState.Visible
import com.gb.opaltest.core.translations.R.string as translations

@Composable
fun HomeRewardBenefitsDialog(
    viewState: HomeRewardBenefitsDialogViewState
) {

    Crossfade(viewState) { it ->
        if (it is Visible) {
            HomeRewardBenefitsDialogContent(it)
        }
    }
}

@Composable
fun HomeRewardBenefitsDialogContent(
    viewState: Visible
) {

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        val vibrator = context.getSystemService(Vibrator::class.java)
        val numberOfPulses = 3
        val pulseDuration = 50L
        val spaceBetweenPulses = 100L
        val maxAmplitude = 255

        val timings = LongArray(numberOfPulses * 2)
        val amplitudes = IntArray(numberOfPulses * 2)

        for (i in 0 until numberOfPulses) {
            val amplitude =
                (maxAmplitude * (i + 1) / numberOfPulses)
            timings[i * 2] = spaceBetweenPulses
            timings[i * 2 + 1] = pulseDuration
            amplitudes[i * 2] = 0
            amplitudes[i * 2 + 1] = amplitude
        }

        vibrator.vibrate(VibrationEffect.createWaveform(timings, amplitudes, -1))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Colors.Black.copy(alpha = 0.7f)),
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .background(color = Colors.MediumGrey, shape = RoundedCornerShape(16.dp))
                .padding(16.dp),
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Colors.Black, shape = RoundedCornerShape(16.dp))
                    .border(
                        width = 1.dp,
                        color = Colors.LightGrey,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clip(RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                if (viewState is Visible.Gem) {
                    val appThemData = createAppThemeData(
                        context = context,
                        gemDrawableResId = viewState.rewardDrawableResId,
                        gemId = viewState.gemId,
                    )
                    appThemData?.let {
                        val infiniteTransition =
                            rememberInfiniteTransition(label = "rotationTransition")
                        val angle by infiniteTransition.animateFloat(
                            initialValue = 0f,
                            targetValue = 360f,
                            animationSpec = infiniteRepeatable(
                                animation = tween(durationMillis = 10000, easing = LinearEasing),
                            ), label = "rotationAnimation"
                        )

                        CompositionLocalProvider(LocalAppThemeData provides appThemData) {
                            AmbientBackground(
                                modifier = Modifier.rotate(angle),
                                imageScaleX = 2f,
                                offset = 0f
                            ) { }
                        }
                    }
                }
                Image(
                    modifier = Modifier.heightIn(min = 256.dp),
                    painter = painterResource(id = viewState.rewardDrawableResId),
                    contentDescription = null,
                )
            }

            Title(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                text = viewState.title.toValue(context),
                textAlign = TextAlign.Center,
                textWeight = TextWeight.BOLD,
                textSize = TextSize.LARGE,
                textColor = Colors.White,
            )

            Body(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                text = viewState.subtitle.toValue(context),
                textAlign = TextAlign.Center,
                textWeight = TextWeight.BOLD,
                textSize = TextSize.LARGE,
                textColor = Colors.LightGrey,
            )

            Spacer(modifier = Modifier.height(32.dp))

            if (viewState is Visible.Gem) {
                val appThemData = createAppThemeData(
                    context = context,
                    gemDrawableResId = viewState.rewardDrawableResId,
                    gemId = viewState.gemId,
                )
                appThemData?.let {
                    CompositionLocalProvider(LocalAppThemeData provides appThemData) {
                        PrimaryButton(
                            modifier = Modifier
                                .fillMaxWidth(),
                            content = {
                                Body(
                                    text = stringResource(translations.home_rewards_benefit_dialog_gem_positive_button),
                                    textAlign = TextAlign.Center,
                                    textWeight = TextWeight.SEMI_BOLD,
                                    textSize = TextSize.LARGE,
                                    textColor = Colors.Black,
                                )
                            },
                            onClick = {
                                viewState.onSetGemClicked(viewState.rewardId, viewState.gemId)
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
            }
            SecondaryButton(
                modifier = Modifier
                    .fillMaxWidth(),
                content = {
                    Body(
                        text = viewState.closeButtonText.toValue(context),
                        textAlign = TextAlign.Center,
                        textWeight = TextWeight.SEMI_BOLD,
                        textSize = TextSize.LARGE,
                        textColor = Colors.Black,
                    )
                },
                onClick = {
                    viewState.onCloseButtonClicked(viewState.rewardId)
                },
            )
        }
    }
}
