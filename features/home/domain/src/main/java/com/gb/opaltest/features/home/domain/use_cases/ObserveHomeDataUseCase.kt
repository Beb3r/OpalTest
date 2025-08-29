package com.gb.opaltest.features.home.domain.use_cases

import com.gb.opaltest.core.common.combines
import com.gb.opaltest.features.home.domain.models.HomeDataDomainModel
import com.gb.opaltest.features.home.domain.models.HomeRewardDomainModel
import com.gb.opaltest.features.referral.domain.use_cases.ObserveReferredUsersUseCase
import com.gb.opaltest.features.rewards.domain.use_cases.ObserveClaimedRewardIdsUseCase
import com.gb.opaltest.features.rewards.domain.use_cases.ObserveRewardsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import org.koin.core.annotation.Factory

@Factory
class ObserveHomeDataUseCase(
    private val observeRewardsUseCase: ObserveRewardsUseCase,
    private val observeReferredUsersUseCase: ObserveReferredUsersUseCase,
    private val observeClaimedRewardIdsUseCase: ObserveClaimedRewardIdsUseCase,
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<HomeDataDomainModel> =
        combines(
            observeRewardsUseCase(),
            observeReferredUsersUseCase(),
            observeClaimedRewardIdsUseCase(),
        ).mapLatest { (rewards, referredUsers, claimedRewardIds) ->
            val referredUsersCount = referredUsers.size
            HomeDataDomainModel(
                referredUsers = referredUsers.toList(),
                rewards = rewards.map { reward ->
                    HomeRewardDomainModel(
                        id = reward.id,
                        threshold = reward.threshold,
                        state = when {
                            referredUsersCount < reward.threshold -> HomeRewardDomainModel.HomeRewardStateDomainModel.InProgress(
                                progress = referredUsersCount,
                                total = reward.threshold,
                            )
                            else ->
                                if (claimedRewardIds.contains(reward.id)) {
                                    HomeRewardDomainModel.HomeRewardStateDomainModel.Claimed
                                } else {
                                    HomeRewardDomainModel.HomeRewardStateDomainModel.Unclaimed(
                                        benefits = reward.benefits
                                    )
                                }

                        }
                    )
                }
            )
        }
}
