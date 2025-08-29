package com.gb.opaltest.features.rewards.domain.models

data class RewardDomainModel(
    val id: String,
    val threshold: Int,
    val benefits: RewardBenefitsDomainModel,
) {
    companion object {
        const val REWARD_ID_1 = "reward_1"
        const val REWARD_ID_2 = "reward_2"
        const val REWARD_ID_3 = "reward_3"
        const val REWARD_ID_4 = "reward_4"
        const val REWARD_ID_5 = "reward_5"
        const val REWARD_ID_6 = "reward_6"
    }
}

sealed interface RewardBenefitsDomainModel{
    data class Gem(val gemId: String): RewardBenefitsDomainModel
    data object FreeSubscription2years: RewardBenefitsDomainModel
    data object FreeSubscriptionLifeTime: RewardBenefitsDomainModel
    data object Gift: RewardBenefitsDomainModel
}
