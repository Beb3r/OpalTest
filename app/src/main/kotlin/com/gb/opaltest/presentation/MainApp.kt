package com.gb.opaltest.presentation

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
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gb.opaltest.core.design.AppThemeData
import com.gb.opaltest.core.design.Colors
import com.gb.opaltest.core.design.LocalAppThemeData
import com.gb.opaltest.core.design.OpalTestTheme
import com.gb.opaltest.core.design.components.AmbientBackground
import com.gb.opaltest.core.design.createAppThemeData
import com.gb.opaltest.core.navigation.api.NavControllerAccessor
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
            createAppThemeData(
                context = context,
                gemDrawableResId = currentGem.drawableResId,
                gemId = currentGem.id
            )?.let {
                Timber.d("TEEST mainapp appData:$it")
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
