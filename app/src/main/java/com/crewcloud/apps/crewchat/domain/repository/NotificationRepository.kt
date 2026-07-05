package com.crewcloud.apps.crewchat.domain.repository

/**
 * Created by BM Anderson on 5/7/26.
 */
interface NotificationRepository {
    suspend fun getFCMToken(): String?
    suspend fun saveFcmToken(value: String)
    suspend fun sendFCMToServer(value: String)
}