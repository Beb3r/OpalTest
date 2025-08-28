package com.gb.opaltest.features.rewards.domain.use_cases

import com.gb.opaltest.features.rewards.domain.repositories.RewardsRepository
import org.koin.core.annotation.Factory

@Factory
class ClearClaimedRewardsUseCase(
    private val repository: RewardsRepository,
) {
    suspend operator fun invoke() {
        repository.clearClaimedRewards()
    }
}
