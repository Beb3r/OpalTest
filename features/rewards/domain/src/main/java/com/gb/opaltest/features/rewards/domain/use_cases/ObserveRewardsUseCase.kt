package com.gb.opaltest.features.rewards.domain.use_cases

import com.gb.opaltest.features.rewards.domain.models.RewardDomainModel
import com.gb.opaltest.features.rewards.domain.repositories.RewardsRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
class ObserveRewardsUseCase(
    private val repository: RewardsRepository
) {

    operator fun invoke(): Flow<List<RewardDomainModel>> = repository.observeRewards()
}
