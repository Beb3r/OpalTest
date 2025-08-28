package com.gb.opaltest.features.referral.domain.repositories

import com.gb.opaltest.features.referral.domain.data_sources.ReferralLocalDataSource
import com.gb.opaltest.features.referral.domain.models.ReferredUserDomainModel
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single

@Single
class ReferralRepository(
    private val dataSource: ReferralLocalDataSource,
) {

    fun observeReferredUsers(): Flow<Set<ReferredUserDomainModel>> =
        dataSource.observeReferredUsers()

    suspend fun setReferredUsers(users: Set<ReferredUserDomainModel>) {
        dataSource.setReferredUsers(users = users)
    }
}
