package com.crewcloud.apps.crewchat.domain.repository

import com.crewcloud.apps.crewchat.domain.model.CheckApi
import com.crewcloud.apps.crewchat.domain.model.CheckSSL
import com.crewcloud.apps.crewchat.domain.model.User
import com.crewcloud.apps.crewchat.domain.model.Result

/**
 * Created by BM Anderson on 2/7/26.
 */
interface AuthRepository {
    suspend fun getSessionId(): String?
    suspend fun loginV5(domain: String, userName: String, password: String): Result<User>
    suspend fun loginCrewChat(domain: String, userName: String, password: String): Result<User>
    suspend fun checkSSL(domain: String): Result<CheckSSL>
    suspend fun checkLoginApi(domain: String): Result<CheckApi>

    suspend fun checkApiDeviceAccess(domain: String): Result<CheckApi>
    suspend fun checkDeviceAccess(androidId: String): Result<Boolean>
    suspend fun insertAndroidDevice(): Result<Boolean>

    suspend fun saveDomain(domain: String)
    suspend fun saveUserName(userName: String)
    suspend fun savePassword(password: String)
    suspend fun saveBaseUrl(url: String)
    suspend fun saveDDSServerIp(ip: String)
    suspend fun saveFileServerIp(ip: String)
    suspend fun saveDDSServerPort(port: Int)
    suspend fun saveFileServerPort(port: Int)
    suspend fun saveSessionId(sessionId: String)

    suspend fun getUserName(): String?
    suspend fun getPassword(): String?

    suspend fun clearSession()
}