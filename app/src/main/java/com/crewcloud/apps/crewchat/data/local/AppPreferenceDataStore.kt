package com.crewcloud.apps.crewchat.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import kotlin.math.min

/**
 * Created by BM Anderson on 2/7/26.
 */
interface AppPreferenceDataStore {
    suspend fun saveFcmToken(value: String)

    suspend fun getFCMToken(): String?
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

    suspend fun saveEnableNotification(value: Boolean)
    suspend fun getEnableNotification(): Boolean
    suspend fun saveEnableSound(value: Boolean)
    suspend fun getEnableSound(): Boolean
    suspend fun saveEnableVibrate(value: Boolean)
    suspend fun getEnableVibrate(): Boolean
    suspend fun saveEnableTime(value: Boolean)
    suspend fun getEnableTime(): Boolean

    suspend fun saveEnableNotificationWhenUsingPC(value: Boolean)
    suspend fun getEnableNotificationWhenUsingPC(): Boolean

    suspend fun saveStartNotificationHour(hour: Int)
    suspend fun getStartNotificationHour(): Int
    suspend fun saveStartNotificationMinutes(minute: Int)
    suspend fun getStartNotificationMinutes(): Int
    suspend fun saveEndNotificationHour(hour: Int)
    suspend fun getEndNotificationHour(): Int
    suspend fun saveEndNotificationMinutes(minute: Int)
    suspend fun getEndNotificationMinutes(): Int
}

class AppPreferenceDataStoreImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : AppPreferenceDataStore {
    override suspend fun saveFcmToken(value: String) {
        try {
            dataStore.edit { preferences ->
                preferences[FCM_TOKEN] = value

            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getFCMToken(): String? {
        try {
            val preferences = dataStore.data.first()
            return preferences[FCM_TOKEN]
        } catch (e: IOException) {
            return null
        }
    }

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

    override suspend fun saveEnableNotification(value: Boolean) {
        dataStore.edit { preferences ->
            preferences[ENABLE_NOTIFICATION] = value
        }
    }

    override suspend fun getEnableNotification(): Boolean {
        val preferences = dataStore.data.first()
        return preferences[ENABLE_NOTIFICATION] ?: true
    }

    override suspend fun saveEnableSound(value: Boolean) {
        dataStore.edit { preferences ->
            preferences[ENABLE_SOUND] = value
        }
    }

    override suspend fun getEnableSound(): Boolean {
        val preferences = dataStore.data.first()
        return preferences[ENABLE_SOUND] ?: true
    }

    override suspend fun saveEnableVibrate(value: Boolean) {
        dataStore.edit { preferences ->
            preferences[ENABLE_VIBRATE] = value
        }
    }

    override suspend fun getEnableVibrate(): Boolean {
        val preferences = dataStore.data.first()
        return preferences[ENABLE_VIBRATE] ?: true
    }

    override suspend fun saveEnableTime(value: Boolean) {
        dataStore.edit { preferences ->
            preferences[ENABLE_TIME] = value
        }
    }

    override suspend fun getEnableTime(): Boolean {
        val preferences = dataStore.data.first()
        return preferences[ENABLE_TIME] ?: true
    }

    override suspend fun saveEnableNotificationWhenUsingPC(value: Boolean) {
        dataStore.edit { preferences ->
            preferences[ENABLE_NOTIFICATION_WHEN_USING_PC_VERSION] = value
        }
    }

    override suspend fun getEnableNotificationWhenUsingPC(): Boolean {
        val preferences = dataStore.data.first()
        return preferences[ENABLE_NOTIFICATION_WHEN_USING_PC_VERSION] ?: true
    }

    override suspend fun saveStartNotificationHour(hour: Int) {
        dataStore.edit { preferences ->
            preferences[START_NOTIFICATION_HOUR] = hour
        }
    }

    override suspend fun getStartNotificationHour(): Int {
        val preferences = dataStore.data.first()
        return preferences[START_NOTIFICATION_HOUR] ?: 0
    }

    override suspend fun saveStartNotificationMinutes(minute: Int) {
        dataStore.edit { preferences ->
            preferences[START_NOTIFICATION_MINUTES] = minute
        }
    }

    override suspend fun getStartNotificationMinutes(): Int {
        val preferences = dataStore.data.first()
        return preferences[START_NOTIFICATION_MINUTES] ?: 0
    }

    override suspend fun saveEndNotificationHour(hour: Int) {
        dataStore.edit { preferences ->
            preferences[END_NOTIFICATION_HOUR] = hour
        }
    }

    override suspend fun getEndNotificationHour(): Int {
        val preferences = dataStore.data.first()
        return preferences[END_NOTIFICATION_HOUR] ?: 0
    }

    override suspend fun saveEndNotificationMinutes(minute: Int) {
        dataStore.edit { preferences ->
            preferences[END_NOTIFICATION_MINUTES] = minute
        }
    }

    override suspend fun getEndNotificationMinutes(): Int {
        val preferences = dataStore.data.first()
        return preferences[END_NOTIFICATION_MINUTES] ?: 0
    }

    companion object PreferenceKeys {
        val FCM_TOKEN = stringPreferencesKey("fcm_token")
        val BASE_URL = stringPreferencesKey("base_url")
        val DOMAIN = stringPreferencesKey("domain")
        val DDS_SERVER_IP = stringPreferencesKey("dds_server_ip")
        val DDS_SERVER_PORT = intPreferencesKey("dds_server_port")
        val FILE_SERVER_IP = stringPreferencesKey("file_server_ip")
        val FILE_SERVER_PORT = intPreferencesKey("file_server_port")

        val ENABLE_NOTIFICATION = booleanPreferencesKey("enable_notification")
        val ENABLE_SOUND = booleanPreferencesKey("enable_notification_sound")
        val ENABLE_VIBRATE = booleanPreferencesKey("enable_notification_vibrate")
        val ENABLE_TIME = booleanPreferencesKey("enable_notification_time")
        val ENABLE_NOTIFICATION_WHEN_USING_PC_VERSION = booleanPreferencesKey(
            "enable_notification_when_using_pc_version"
        )

        val START_NOTIFICATION_HOUR = intPreferencesKey("start_notification_time_hour")
        val START_NOTIFICATION_MINUTES = intPreferencesKey("start_notification_time_minutes")
        val END_NOTIFICATION_HOUR = intPreferencesKey("end_notification_time_hour")
        val END_NOTIFICATION_MINUTES = intPreferencesKey("end_notification_time_minutes")
    }
}