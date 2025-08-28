package com.gb.opaltest.features.gems.domain.use_cases

import com.gb.opaltest.features.gems.domain.models.GemDomainModel
import com.gb.opaltest.features.gems.domain.models.GemDomainModel.Companion.GEM_ID_B1
import com.gb.opaltest.features.gems.domain.repositories.GemsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Factory

@Factory
class ObserveCurrentGemUseCase(
    private val repository: GemsRepository,
) {
    operator fun invoke(): Flow<GemDomainModel> {
        return repository.observeCurrentGem().map {
            it ?: GemDomainModel(id = GEM_ID_B1)
        }
    }
}
