package com.gb.opaltest.features.gems.domain.use_cases

import com.gb.opaltest.features.gems.domain.repositories.GemsRepository
import org.koin.core.annotation.Factory

@Factory
class SetCurrentGemUseCase(
    private val repository: GemsRepository,
) {
    suspend operator fun invoke(id: String) {
        repository.setCurrentGemId(id)
    }
}
