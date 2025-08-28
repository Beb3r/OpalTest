package com.gb.opaltest.features.rewards.data.data_sources

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.gb.opaltest.core.common.observeKey
import com.gb.opaltest.features.rewards.domain.data_sources.RewardsLocalDataSource
import com.gb.opaltest.features.rewards.domain.models.RewardDomainModel
import com.gb.opaltest.features.rewards.domain.models.RewardDomainModel.Companion.REWARD_ID_1
import com.gb.opaltest.features.rewards.domain.models.RewardDomainModel.Companion.REWARD_ID_2
import com.gb.opaltest.features.rewards.domain.models.RewardDomainModel.Companion.REWARD_ID_3
import com.gb.opaltest.features.rewards.domain.models.RewardDomainModel.Companion.REWARD_ID_4
import com.gb.opaltest.features.rewards.domain.models.RewardDomainModel.Companion.REWARD_ID_5
import com.gb.opaltest.features.rewards.domain.models.RewardDomainModel.Companion.REWARD_ID_6
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import okio.Path.Companion.toPath
import org.koin.core.annotation.Single

@Single(binds = [RewardsLocalDataSource::class])
class RewardsLocalDataSourceImpl(
    context: Context,
) : RewardsLocalDataSource {
    companion object {
        private const val PREF_NAME = "b11adabb-20f6-4c01-b977-2a2721557180"

        private val KEY_CLAIMED_REWARD_ID =
            stringSetPreferencesKey("3f242f67-f498-4897-b5ad-f29b60aa325d")
    }

    private val dataStore by lazy {
        PreferenceDataStoreFactory.createWithPath(
            produceFile = { context.filesDir.resolve("${PREF_NAME}.preferences_pb").absolutePath.toPath() },
        )
    }

    private val rewards by lazy {
        listOf(
            RewardDomainModel(id = REWARD_ID_1, threshold = 1),
            RewardDomainModel(id = REWARD_ID_2, threshold = 3),
            RewardDomainModel(id = REWARD_ID_3, threshold = 10),
            RewardDomainModel(id = REWARD_ID_4, threshold = 20),
            RewardDomainModel(id = REWARD_ID_5, threshold = 50),
            RewardDomainModel(id = REWARD_ID_6, threshold = 100),

            )
    }
    private val rewardsFlow = MutableSharedFlow<List<RewardDomainModel>>(replay = 1)

    init {
        rewardsFlow.tryEmit(rewards)
    }

    override fun observeRewards(): Flow<List<RewardDomainModel>> = rewardsFlow.asSharedFlow()

    override fun observeClaimedRewardIds(): Flow<Set<String>> =
        dataStore.observeKey(
            key = KEY_CLAIMED_REWARD_ID,
            defaultValue = emptySet(),
        ).map { it ?: emptySet() }

    override suspend fun setClaimedRewardId(id: String) {
        dataStore.edit { preferences ->
            val ids = preferences[KEY_CLAIMED_REWARD_ID] ?: emptySet()
            preferences[KEY_CLAIMED_REWARD_ID] = ids + id
        }
    }
}
