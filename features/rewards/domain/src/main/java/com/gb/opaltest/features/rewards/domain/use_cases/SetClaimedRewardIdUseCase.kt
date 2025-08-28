package com.gb.opaltest.features.rewards.domain.use_cases

import com.gb.opaltest.features.rewards.domain.repositories.RewardsRepository
import org.koin.core.annotation.Factory

@Factory
class SetClaimedRewardIdUseCase(
    private val repository: RewardsRepository
) {

    suspend operator fun invoke(id: String) {
        repository.setClaimedRewardId(id = id)
    }
}
