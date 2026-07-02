package com.crewcloud.apps.crewchat.domain.repository

/**
 * Created by BM Anderson on 2/7/26.
 */
interface AuthRepository {
    suspend fun getSessionId(): String?
}