package com.gb.opaltest.features.referral.domain.data_sources

import com.gb.opaltest.features.referral.domain.models.ReferredUserDomainModel
import kotlinx.coroutines.flow.Flow

interface ReferralLocalDataSource {

    fun observeReferredUsers(): Flow<Set<ReferredUserDomainModel>>

    suspend fun setReferredUsers(users: Set<ReferredUserDomainModel>)

    suspend fun clearReferredUsers()
}
