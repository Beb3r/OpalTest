package com.gb.opaltest.features.gems.domain.data_sources

import com.gb.opaltest.features.gems.domain.models.GemDomainModel
import kotlinx.coroutines.flow.Flow

interface GemsLocalDataSource {

    fun observeCurrentGem(): Flow<GemDomainModel?>
    fun observeGems(): Flow<List<GemDomainModel>>
    suspend fun setCurrentGemId(id: String)
    suspend fun clearCurrentGem()
}
