package com.crewcloud.apps.crewchat.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 * Created by BM Anderson on 2/7/26.
 */
interface AppPreferenceDataStore {
    suspend fun saveBaseUrl(url: String)
    suspend fun getBaseUrl(): String
}

class AppPreferenceDataStoreImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : AppPreferenceDataStore {
    override suspend fun saveBaseUrl(url: String) {
        dataStore.edit { preferences ->
            preferences[BASE_URL] = url

        }
    }

    override suspend fun getBaseUrl(): String {
        val preferences = dataStore.data.first()
        return preferences[BASE_URL] ?: ""
    }

    companion object PreferenceKeys {
        val BASE_URL = stringPreferencesKey("base_url")
    }
}