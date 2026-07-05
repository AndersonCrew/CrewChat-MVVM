package com.crewcloud.apps.crewchat.data.repository

import com.crewcloud.apps.crewchat.data.local.AppPreferenceDataStore
import com.crewcloud.apps.crewchat.data.network.api.DazoneApiService
import com.crewcloud.apps.crewchat.domain.repository.NotificationRepository
import javax.inject.Inject

/**
 * Created by BM Anderson on 5/7/26.
 */
class NotificationRepositoryImpl @Inject constructor(
    private val appPreferenceDataStore: AppPreferenceDataStore,
): NotificationRepository {
    override suspend fun getFCMToken(): String? {
        return appPreferenceDataStore.getFCMToken()
    }

    override suspend fun saveFcmToken(value: String) {
        appPreferenceDataStore.saveFcmToken(value)
    }

    override suspend fun sendFCMToServer(value: String) {
        // TODO Send FCM to server api.sendFCM...
    }
}