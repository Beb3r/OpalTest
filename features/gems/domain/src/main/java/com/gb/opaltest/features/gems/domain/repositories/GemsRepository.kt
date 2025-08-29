package com.gb.opaltest.features.gems.domain.repositories

import com.gb.opaltest.features.gems.domain.data_sources.GemsLocalDataSource
import com.gb.opaltest.features.gems.domain.models.GemDomainModel
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single

@Single
class GemsRepository(private val dataSource: GemsLocalDataSource) {

    fun observeCurrentGem(): Flow<GemDomainModel?> = dataSource.observeCurrentGem()

    fun observeGems(): Flow<List<GemDomainModel>> = dataSource.observeGems()

    suspend fun setCurrentGemId(id: String) {
        dataSource.setCurrentGemId(id = id)
    }

    suspend fun clearCurrentGem() {
        dataSource.clearCurrentGem()
    }
}