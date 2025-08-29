package com.gb.opaltest.features.splash_screen.presentation

import android.graphics.RuntimeShader
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.gb.opaltest.features.splash_screen.presentation.shaders.dotsShaderSource
import com.gb.opaltest.features.splash_screen.presentation.shaders.smokeShaderSource
import org.koin.androidx.compose.koinViewModel
import com.gb.opaltest.core.design.R.drawable as drawables

@Composable
fun SplashScreen(
    viewModel: SplashScreenViewModel = koinViewModel(),
) {
    LaunchedEffect(viewModel) {
        viewModel.onViewDisplayed()
    }

    // SMOKE
    val smokeInfiniteTransition = rememberInfiniteTransition(label = "shader_transition")
    val smokeTime by smokeInfiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 30f,
        animationSpec = infiniteRepeatable(
            animation = tween(60000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "time"
    )
    val smokeShader = remember { RuntimeShader(smokeShaderSource) }

    // DOTS
    val dotsInfiniteTransition = rememberInfiniteTransition(label = "shader_transition")
    val dotsTime by dotsInfiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 30f,
        animationSpec = infiniteRepeatable(
            animation = tween(60000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "time"
    )
    val dotsShader = remember { RuntimeShader(dotsShaderSource) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .drawWithCache {
                dotsShader.setFloatUniform("resolution", size.width, size.height)
                dotsShader.setFloatUniform("scale", 3.0f)
                dotsShader.setFloatUniform("time", dotsTime)
                val dotsShaderBrush = ShaderBrush(dotsShader)

                smokeShader.setFloatUniform("resolution", size.width, size.height)
                smokeShader.setFloatUniform("time", smokeTime)
                val smokeShaderBrush = ShaderBrush(smokeShader)

                onDrawBehind {
                    drawRect(dotsShaderBrush)
                    drawRect(smokeShaderBrush)
                }
            }
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            painter = painterResource(id = drawables.embossed_seal),
            contentDescription = "splash screen embossed seal",
        )

        Image(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 64.dp, end = 64.dp),
            painter = painterResource(id = drawables.logo),
            contentDescription = "splash screen logo",
        )
    }
}
