package com.gb.opaltest.features.referral.domain.use_cases

import com.gb.opaltest.features.referral.domain.models.ReferredUserDomainModel
import com.gb.opaltest.features.referral.domain.repositories.ReferralRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
class ObserveReferredUsersUseCase(
    private val repository: ReferralRepository
) {

    operator fun invoke(): Flow<Set<ReferredUserDomainModel>> = repository.observeReferredUsers()
}
