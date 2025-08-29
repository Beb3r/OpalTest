package com.gb.opaltest.features.home.domain.models

import com.gb.opaltest.features.referral.domain.models.ReferredUserDomainModel
import com.gb.opaltest.features.rewards.domain.models.RewardBenefitsDomainModel

data class HomeDataDomainModel(
    val referredUsers: List<ReferredUserDomainModel>,
    val rewards: List<HomeRewardDomainModel>,
)

data class HomeRewardDomainModel(
    val id: String,
    val threshold: Int,
    val state: HomeRewardStateDomainModel,
) {
    sealed interface HomeRewardStateDomainModel {
        object Claimed : HomeRewardStateDomainModel
        data class Unclaimed(val benefits: RewardBenefitsDomainModel) : HomeRewardStateDomainModel
        data class InProgress(val progress: Int, val total: Int) : HomeRewardStateDomainModel
    }
}
