package com.gb.opaltest.features.rewards.domain.use_cases

import com.gb.opaltest.features.rewards.domain.repositories.RewardsRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
class ObserveClaimedRewardIdsUseCase(
    private val repository: RewardsRepository
) {

    operator fun invoke(): Flow<Set<String>> = repository.observeClaimedRewardIds()
}
