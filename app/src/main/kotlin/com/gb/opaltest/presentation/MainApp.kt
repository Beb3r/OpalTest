package com.gb.opaltest.presentation

import android.content.Context
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.palette.graphics.Palette
import com.gb.opaltest.core.design.AppThemeData
import com.gb.opaltest.core.design.Colors
import com.gb.opaltest.core.design.LocalAppThemeData
import com.gb.opaltest.core.design.OpalTestTheme
import com.gb.opaltest.core.design.components.AmbientBackground
import com.gb.opaltest.core.navigation.api.NavControllerAccessor
import com.gb.opaltest.features.gems.domain.models.GemDomainModel.Companion.GEM_ID_B1
import com.gb.opaltest.features.gems.presentation.models.GemUiModel
import com.gb.opaltest.features.home.navigation.HomeScreenRoute
import com.gb.opaltest.features.home.presentation.HomeScreen
import com.gb.opaltest.features.splash_screen.navigation.SplashScreenRoute
import com.gb.opaltest.features.splash_screen.presentation.SplashScreen
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber

@Composable
fun MainApp(
    navControllerAccessor: NavControllerAccessor,
    viewModel: MainAppViewModel = koinViewModel(),
) {
    val context = LocalContext.current

    var localAppThemeData by remember {
        mutableStateOf(
            AppThemeData()
        )
    }

    LaunchedEffect(viewModel) {
        viewModel.currentGemFlow.collect { currentGem ->
            createAppThemeData(context = context, gem = currentGem)?.let {
                localAppThemeData = it
            }
        }
    }

    CompositionLocalProvider(LocalAppThemeData provides localAppThemeData) {
        OpalTestTheme {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Colors.Black)
            ) {
                val navController = rememberNavController()
                var ambienBackgroundOffset by remember {
                    mutableFloatStateOf(0f)
                }

                DisposableEffect(navController) {
                    navControllerAccessor.setController(navController)
                    onDispose {
                        navControllerAccessor.clear()
                    }
                }

                NavHost(
                    navController = navController,
                    startDestination = SplashScreenRoute,
                    enterTransition = { slideInHorizontally { it } },
                    exitTransition = { slideOutHorizontally { -it } },
                    popEnterTransition = { slideInHorizontally { -it } },
                    popExitTransition = { slideOutHorizontally { it } },
                ) {
                    composable<SplashScreenRoute> {
                        SplashScreen()
                    }
                    composable<HomeScreenRoute> {
                        AmbientBackground(
                            offset = ambienBackgroundOffset,
                            content = {
                                HomeScreen(
                                    onScrolled = {
                                        Timber.d("TEEST $it")
                                        ambienBackgroundOffset = it
                                    }
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

private fun createAppThemeData(context: Context, gem: GemUiModel): AppThemeData? {
    val drawableResId = gem.drawableResId
    val bitmap = AppCompatResources.getDrawable(context, drawableResId)?.toBitmap()
    if (bitmap == null) {
        Timber.e("createAppThemeData: bitmap is null for drawableResId=$drawableResId")
        return null
    }
    val palette = Palette.from(bitmap).generate()
    val (mainColor, secondaryColor) = if (gem.id == GEM_ID_B1) {
        // for first gem, using colors from screenshot test web page to match at best
        Colors.LightGreen to Colors.LightBlue
    } else {
        (palette.vibrantSwatch?.let { Color(it.rgb) } ?: Colors.Black) to
                (palette.lightVibrantSwatch?.let { Color(it.rgb) } ?: Colors.Black)
    }
    return AppThemeData(
        ambientBackgroundGemDrawableResId = drawableResId,
        mainColor = mainColor,
        secondaryColor = secondaryColor,
    )
}
