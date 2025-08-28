package com.gb.opaltest.features.gems.domain.use_cases

import com.gb.opaltest.features.gems.domain.models.GemDomainModel
import com.gb.opaltest.features.gems.domain.repositories.GemsRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
class ObserveGemsUseCase(
    private val repository: GemsRepository,
) {
    operator fun invoke(): Flow<List<GemDomainModel>> = repository.observeGems()

}