package com.crewcloud.apps.crewchat.data.local

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import androidx.core.content.edit

/**
 * Created by BM Anderson on 2/7/26.
 */
interface SecureLocalStorage {
    suspend fun saveSessionId(value: String)

    suspend fun saveUserName(userName: String)
    suspend fun savePassword(password: String)
    suspend fun getSessionId(): String?
    suspend fun getUserName(): String?
    suspend fun getPassword(): String?

    suspend fun clearSession()
}

class SecureLocalStorageImpl @Inject constructor(
    @ApplicationContext context: Context
) : SecureLocalStorage {

    private companion object {
        const val KEY_SESSION_ID = "session_id"
        const val USERNAME = "user_name"
        const val PASSWORD = "password"
    }

    private val masterKey =
        MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()

    private val prefs = EncryptedSharedPreferences.create(
        context,
        "secure_local_storage",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    override suspend fun saveSessionId(value: String) {
        prefs.edit { putString(KEY_SESSION_ID, value) }
    }

    override suspend fun saveUserName(userName: String) {
        prefs.edit { putString(USERNAME, userName) }
    }

    override suspend fun savePassword(password: String) {
        prefs.edit { putString(PASSWORD, password) }
    }

    override suspend fun getSessionId(): String? {
        return prefs.getString(KEY_SESSION_ID, null)
    }

    override suspend fun getUserName(): String? {
        return prefs.getString(USERNAME, null)
    }

    override suspend fun getPassword(): String? {
        return prefs.getString(PASSWORD, null)
    }

    override suspend fun clearSession() {
        prefs.edit {
            clear()
        }
    }
}