package com.gb.opaltest.core.navigation.api_impl

import androidx.navigation.navOptions
import com.gb.opaltest.core.navigation.api_impl.manager.NavigationCommand
import com.gb.opaltest.core.navigation.api_impl.manager.NavigationManager
import com.gb.opaltest.features.home.navigation.HomeScreenRoute
import com.gb.opaltest.features.splash_screen.navigation.SplashScreenNavigation
import com.gb.opaltest.features.splash_screen.navigation.SplashScreenRoute
import org.koin.core.annotation.Single

@Single(binds = [SplashScreenNavigation::class])
class SplashScreenNavigationImpl(
    private val navigationManager: NavigationManager
) : SplashScreenNavigation {

    override fun navigateToHome() {
        navigationManager.navigate(
            command = NavigationCommand.NavigateToRoute(
                route = HomeScreenRoute,
                options = navOptions {
                    popUpTo(SplashScreenRoute) {
                        inclusive = true
                    }
                }
            )
        )
    }
}
