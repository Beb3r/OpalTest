package com.gb.opaltest.features.referral.domain.use_cases

import com.gb.opaltest.features.referral.domain.models.ReferredUserDomainModel
import com.gb.opaltest.features.referral.domain.repositories.ReferralRepository
import org.koin.core.annotation.Factory
import java.util.Date

@Factory
class SetReferredUsersUseCase(
    private val repository: ReferralRepository
) {

    suspend operator fun invoke(count: Int) {
        val users = buildSet {
            repeat(count) {
                val id = (this.size + 1).toString()
                add(
                    ReferredUserDomainModel(
                        id = id,
                        name = "User $id",
                        date = Date()
                    )
                )
            }
        }
        repository.setReferredUsers(users = users)
    }
}
