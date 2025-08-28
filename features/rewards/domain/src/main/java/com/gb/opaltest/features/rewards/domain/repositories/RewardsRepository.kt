package com.gb.opaltest.features.rewards.domain.repositories

import com.gb.opaltest.features.rewards.domain.data_sources.RewardsLocalDataSource
import com.gb.opaltest.features.rewards.domain.models.RewardDomainModel
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single

@Single
class RewardsRepository(
    private val dataSource: RewardsLocalDataSource,
) {

    fun observeRewards(): Flow<List<RewardDomainModel>> = dataSource.observeRewards()

    fun observeClaimedRewardIds(): Flow<Set<String>> = dataSource.observeClaimedRewardIds()

    suspend fun setClaimedRewardId(id: String) {
        dataSource.setClaimedRewardId(id = id)
    }

    suspend fun clearClaimedRewards() {
        dataSource.clearClaimedRewards()
    }
}
