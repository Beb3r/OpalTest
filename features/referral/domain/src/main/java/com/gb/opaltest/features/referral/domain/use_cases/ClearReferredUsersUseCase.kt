package com.gb.opaltest.features.referral.domain.use_cases

import com.gb.opaltest.features.referral.domain.repositories.ReferralRepository
import org.koin.core.annotation.Factory

@Factory
class ClearReferredUsersUseCase(
    private val repository: ReferralRepository
) {

    suspend operator fun invoke() {
        repository.clearReferredUsers()
    }
}
