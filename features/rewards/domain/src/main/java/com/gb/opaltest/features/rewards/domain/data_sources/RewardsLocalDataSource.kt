package com.gb.opaltest.features.rewards.domain.data_sources

import com.gb.opaltest.features.rewards.domain.models.RewardDomainModel
import kotlinx.coroutines.flow.Flow

interface RewardsLocalDataSource {

    fun observeRewards(): Flow<List<RewardDomainModel>>

    fun observeClaimedRewardIds(): Flow<Set<String>>

    suspend fun setClaimedRewardId(id: String)
}
