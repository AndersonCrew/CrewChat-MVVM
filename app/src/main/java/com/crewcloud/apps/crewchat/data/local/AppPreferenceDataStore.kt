package com.crewcloud.apps.crewchat.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Created by BM Anderson on 2/7/26.
 */
interface AppPreferenceDataStore {
    suspend fun saveBaseUrl(url: String)
    suspend fun getBaseUrl(): String?

    suspend fun saveDomain(domain: String)

    suspend fun getDomain(): String?

    suspend fun saveDDSServerIP(ip: String)
    suspend fun getDDSServerIP(): String?
    suspend fun saveDDSServerPort(port: Int)
    suspend fun getDDSServerPort(): Int?

    suspend fun saveFileServerIP(ip: String)
    suspend fun getFileServerIP(): String?

    suspend fun saveFileServerPort(port: Int)
    suspend fun getFileServerPort(): Int?
}

class AppPreferenceDataStoreImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : AppPreferenceDataStore {
    override suspend fun saveBaseUrl(url: String) {
        try {
            dataStore.edit { preferences ->
                preferences[BASE_URL] = url

            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getBaseUrl(): String? {
        try {
            val preferences = dataStore.data.first()
            return preferences[BASE_URL]
        } catch (e: IOException) {
            return null
        }
    }

    override suspend fun saveDomain(domain: String) {
        try {
            dataStore.edit { preferences ->
                preferences[DOMAIN] = domain
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getDomain(): String? {
        val preferences = dataStore.data.first()
        return preferences[DOMAIN]
    }

    override suspend fun saveDDSServerIP(ip: String) {
        dataStore.edit { preferences ->
            preferences[DDS_SERVER_IP] = ip
        }
    }

    override suspend fun getDDSServerIP(): String? {
        val preferences = dataStore.data.first()
        return preferences[DDS_SERVER_IP]
    }

    override suspend fun saveDDSServerPort(port: Int) {
        dataStore.edit { preferences ->
            preferences[DDS_SERVER_PORT] = port
        }
    }

    override suspend fun getDDSServerPort(): Int? {
        val preferences = dataStore.data.first()
        return preferences[DDS_SERVER_PORT]
    }

    override suspend fun saveFileServerIP(ip: String) {
        dataStore.edit { preferences ->
            preferences[FILE_SERVER_IP] = ip
        }
    }

    override suspend fun getFileServerIP(): String? {
        val preferences = dataStore.data.first()
        return preferences[FILE_SERVER_IP]
    }

    override suspend fun saveFileServerPort(port: Int) {
        dataStore.edit { preferences ->
            preferences[FILE_SERVER_PORT] = port
        }
    }

    override suspend fun getFileServerPort(): Int? {
        val preferences = dataStore.data.first()
        return preferences[FILE_SERVER_PORT]
    }

    companion object PreferenceKeys {
        val BASE_URL = stringPreferencesKey("base_url")
        val DOMAIN = stringPreferencesKey("domain")
        val DDS_SERVER_IP = stringPreferencesKey("dds_server_ip")
        val DDS_SERVER_PORT = intPreferencesKey("dds_server_port")
        val FILE_SERVER_IP = stringPreferencesKey("file_server_ip")
        val FILE_SERVER_PORT = intPreferencesKey("file_server_port")
    }
}