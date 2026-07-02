package com.crewcloud.apps.crewchat.data.repository

import com.crewcloud.apps.crewchat.data.local.SecureLocalStorage
import com.crewcloud.apps.crewchat.domain.repository.AuthRepository
import javax.inject.Inject

/**
 * Created by BM Anderson on 2/7/26.
 */
class AuthRepositoryImpl @Inject constructor(private val secureLocalStorage: SecureLocalStorage) :
    AuthRepository {
    override suspend fun getSessionId(): String? {
        return secureLocalStorage.getSessionId()
    }
}