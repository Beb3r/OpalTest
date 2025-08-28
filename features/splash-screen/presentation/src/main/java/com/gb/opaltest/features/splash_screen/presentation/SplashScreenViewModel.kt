package com.gb.opaltest.features.splash_screen.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gb.opaltest.features.splash_screen.navigation.SplashScreenNavigation
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class SplashScreenViewModel(
    private val navigation: SplashScreenNavigation,
) : ViewModel() {

    fun onViewDisplayed() {
        viewModelScope.launch {
            delay(500)
            navigation.navigateToHome()
        }
    }
}