package com.gb.opaltest.features.referral.data.data_sources

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.gb.opaltest.core.common.observeKey
import com.gb.opaltest.features.referral.domain.data_sources.ReferralLocalDataSource
import com.gb.opaltest.features.referral.domain.models.ReferredUserDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import okio.Path.Companion.toPath
import org.koin.core.annotation.Single

@Single(binds = [ReferralLocalDataSource::class])
class ReferralLocalDataSourceImpl(
    private val context: Context,
    private val json: Json,
) : ReferralLocalDataSource {
    companion object {
        private const val PREF_NAME = "fecb1d63-1891-4059-b8cf-b1aef8e9f965"

        private val KEY_REFERRED_USERS =
            stringSetPreferencesKey("3f242f67-f498-4897-b5ad-f29b60aa325d")
    }

    private val dataStore by lazy {
        PreferenceDataStoreFactory.createWithPath(
            produceFile = { context.filesDir.resolve("${PREF_NAME}.preferences_pb").absolutePath.toPath() },
        )
    }

    override fun observeReferredUsers(): Flow<Set<ReferredUserDomainModel>> =
        dataStore.observeKey(
            key = KEY_REFERRED_USERS,
            defaultValue = emptySet(),
        ).map {
            it?.map { user ->
                json.decodeFromString<ReferredUserDomainModel>(user)
            }?.toSet() ?: emptySet()
        }

    override suspend fun setReferredUsers(users: Set<ReferredUserDomainModel>) {
        dataStore.edit { preferences ->
            preferences[KEY_REFERRED_USERS] = users.map { json.encodeToString(it) }.toSet()
        }
    }

    override suspend fun clearReferredUsers() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}
