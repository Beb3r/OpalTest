package com.gb.opaltest

import android.app.Application
import com.gb.opaltest.core.common.di.CommonModule
import com.gb.opaltest.core.navigation.api_impl.di.NavigationModule
import com.gb.opaltest.di.AppPresentationModule
import com.gb.opaltest.features.gems.data.di.GemsDataModule
import com.gb.opaltest.features.gems.domain.di.GemsDomainModule
import com.gb.opaltest.features.home.domain.di.HomeDomainModule
import com.gb.opaltest.features.home.presentation.di.HomePresentationModule
import com.gb.opaltest.features.referral.data.di.ReferralDataModule
import com.gb.opaltest.features.referral.domain.di.ReferralDomainModule
import com.gb.opaltest.features.rewards.data.di.RewardsDataModule
import com.gb.opaltest.features.rewards.domain.di.RewardsDomainModule
import com.gb.opaltest.features.splash_screen.presentation.di.SplashScreenPresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.ksp.generated.module
import timber.log.Timber

class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@AppApplication)

            Timber.plant(Timber.DebugTree())

            modules(
                AppPresentationModule().module,
                CommonModule().module,
                GemsDataModule().module,
                GemsDomainModule().module,
                HomeDomainModule().module,
                HomePresentationModule().module,
                NavigationModule().module,
                ReferralDataModule().module,
                ReferralDomainModule().module,
                RewardsDataModule().module,
                RewardsDomainModule().module,
                SplashScreenPresentationModule().module,
            )
        }
    }
}
