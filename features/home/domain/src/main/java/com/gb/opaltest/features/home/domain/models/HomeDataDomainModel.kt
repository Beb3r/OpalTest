package com.gb.opaltest.features.home.domain.models

import com.gb.opaltest.features.referral.domain.models.ReferredUserDomainModel

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
        object Unclaimed : HomeRewardStateDomainModel
        data class InProgress(val progress: Int, val total: Int) : HomeRewardStateDomainModel
    }
}
