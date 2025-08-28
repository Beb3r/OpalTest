package com.gb.opaltest.features.gems.data.data_sources

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.gb.opaltest.core.common.observeKey
import com.gb.opaltest.features.gems.domain.data_sources.GemsLocalDataSource
import com.gb.opaltest.features.gems.domain.models.GemDomainModel
import com.gb.opaltest.features.gems.domain.models.GemDomainModel.Companion.GEM_ID_B1
import com.gb.opaltest.features.gems.domain.models.GemDomainModel.Companion.GEM_ID_C1
import com.gb.opaltest.features.gems.domain.models.GemDomainModel.Companion.GEM_ID_D1
import com.gb.opaltest.features.gems.domain.models.GemDomainModel.Companion.GEM_ID_E1
import com.gb.opaltest.features.gems.domain.models.GemDomainModel.Companion.GEM_ID_F1
import com.gb.opaltest.features.gems.domain.models.GemDomainModel.Companion.GEM_ID_G1
import com.gb.opaltest.features.gems.domain.models.GemDomainModel.Companion.GEM_ID_H1
import com.gb.opaltest.features.gems.domain.models.GemDomainModel.Companion.GEM_ID_J1
import com.gb.opaltest.features.gems.domain.models.GemDomainModel.Companion.GEM_ID_K1
import com.gb.opaltest.features.gems.domain.models.GemDomainModel.Companion.GEM_ID_L1
import com.gb.opaltest.features.gems.domain.models.GemDomainModel.Companion.GEM_ID_M1
import com.gb.opaltest.features.gems.domain.models.GemDomainModel.Companion.GEM_ID_N1
import com.gb.opaltest.features.gems.domain.models.GemDomainModel.Companion.GEM_ID_O1
import com.gb.opaltest.features.gems.domain.models.GemDomainModel.Companion.GEM_ID_P1
import com.gb.opaltest.features.gems.domain.models.GemDomainModel.Companion.GEM_ID_Q1
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import okio.Path.Companion.toPath
import org.koin.core.annotation.Single

@Single(binds = [GemsLocalDataSource::class])
class GemsLocalDataSourceImpl(
    context: Context
) : GemsLocalDataSource {

    companion object {
        private const val PREF_NAME = "164f1b75-f1f6-4045-bdae-13ed0175600e"

        private val KEY_CURRENT_GEM_ID =
            stringPreferencesKey("1192487f-c02a-4bc2-9d9e-1faa6ec8e96f")
    }

    private val dataStore = PreferenceDataStoreFactory.createWithPath(
        produceFile = { context.filesDir.resolve("${PREF_NAME}.preferences_pb").absolutePath.toPath() },
    )

    private val gems by lazy {
        listOf(
            GemDomainModel(id = GEM_ID_B1),
            GemDomainModel(id = GEM_ID_C1),
            GemDomainModel(id = GEM_ID_D1),
            GemDomainModel(id = GEM_ID_E1),
            GemDomainModel(id = GEM_ID_F1),
            GemDomainModel(id = GEM_ID_G1),
            GemDomainModel(id = GEM_ID_H1),
            GemDomainModel(id = GEM_ID_J1),
            GemDomainModel(id = GEM_ID_K1),
            GemDomainModel(id = GEM_ID_L1),
            GemDomainModel(id = GEM_ID_M1),
            GemDomainModel(id = GEM_ID_N1),
            GemDomainModel(id = GEM_ID_O1),
            GemDomainModel(id = GEM_ID_P1),
            GemDomainModel(id = GEM_ID_Q1),
        )
    }
    private val gemsFlow = MutableSharedFlow<List<GemDomainModel>>(replay = 1)

    init {
        gemsFlow.tryEmit(gems)
    }

    override fun observeCurrentGem(): Flow<GemDomainModel?> =
        dataStore.observeKey(
            key = KEY_CURRENT_GEM_ID,
            defaultValue = null,
        ).map {
            it?.let { id ->
                gems.firstOrNull { it.id == id }
            }
        }

    override fun observeGems(): Flow<List<GemDomainModel>> = gemsFlow.asSharedFlow()

    override suspend fun setCurrentGemId(id: String) {
        dataStore.edit { preferences ->
            preferences[KEY_CURRENT_GEM_ID] = id
        }
    }
}
