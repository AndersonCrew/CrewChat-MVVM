package com.crewcloud.apps.crewchat.domain.usecase

import android.content.Context
import android.provider.Settings
import android.util.Log
import com.crewcloud.apps.crewchat.domain.model.Result
import com.crewcloud.apps.crewchat.domain.model.User
import com.crewcloud.apps.crewchat.domain.repository.AuthRepository
import com.crewcloud.apps.crewchat.domain.repository.UserRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

/**
 * Created by BM Anderson on 3/7/26.
 */

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
) {
    suspend fun getDomain(): String? {
        return userRepository.getDomain()
    }

    suspend fun getUserName(): String? {
        return authRepository.getUserName()
    }

    suspend fun getPassword(): String? {
        return authRepository.getPassword()
    }

    suspend operator fun invoke(
        domain: String,
        userName: String,
        password: String,
        androidId: String
    ): Result<User> = coroutineScope {
        val domain = cleanDomain(domain)
        val saveDomainDeferred = async { authRepository.saveDomain(domain) }
        val saveUserNameDeferred = async { authRepository.saveUserName(userName) }
        val savePasswordDeferred = async { authRepository.savePassword(password) }

        awaitAll(saveDomainDeferred, saveUserNameDeferred, savePasswordDeferred)
        val result = authRepository.checkSSL(domain = domain)
        if (result is Result.Failure) {
            return@coroutineScope result
        }


        val ssl = (result as Result.ResultSuccess).result.ssl
        val baseUrl = if (ssl) {
            "https://$domain"
        } else {
            "http://$domain"
        }

        //Save Base URL
        authRepository.saveBaseUrl(baseUrl)
        Log.d("LoginUseCase", "ssl=$ssl domain=$domain baseUrl=$baseUrl")

        val resultCheckApi = authRepository.checkLoginApi(
            domain = domain
        )

        if (resultCheckApi is Result.Failure) {
            return@coroutineScope resultCheckApi
        }

        val isLoginCrewChat = (resultCheckApi as Result.ResultSuccess).result.api

        val resultLogin =
            if (isLoginCrewChat) authRepository.loginCrewChat(
                domain = domain,
                userName = userName,
                password = password
            ) else authRepository.loginV5(
                domain = domain,
                userName = userName,
                password = password
            )

        if (resultLogin is Result.Failure) {
            return@coroutineScope resultLogin
        }

        val hasCheckDeviceApi = authRepository.checkApiDeviceAccess(domain)
        if (hasCheckDeviceApi is Result.Failure) {
            return@coroutineScope hasCheckDeviceApi
        }

        val user = (resultLogin as Result.ResultSuccess).result
        savePersisUser(user)

        if((hasCheckDeviceApi as Result.ResultSuccess).result.api) {
            checkDeviceAccess(androidId, user)
        } else {
            insertAndroidDevice(user)
        }
    }

    suspend fun checkDeviceAccess(androidId: String, user: User) : Result<User>{
        val result = authRepository.checkDeviceAccess(androidId)

        if(result is Result.Failure) {
            authRepository.clearSession()
            userRepository.clearUser()
            return result
        }

        //return insertAndroidDevice(user)
        return Result.ResultSuccess(result = user)
    }

    suspend fun insertAndroidDevice(user: User): Result<User> {
        val result = authRepository.insertAndroidDevice()
        if(result is Result.Failure) {
            authRepository.clearSession()
            userRepository.clearUser()
            return result
        }

        return Result.ResultSuccess(result = user)
    }

    fun cleanDomain(rawDomain: String): String {
        return rawDomain
            .trim()
            .removePrefix("https://")
            .removePrefix("http://")
            .removeSuffix("/")
    }

    suspend fun savePersisUser(user: User) = coroutineScope {
        userRepository.saveUser(user)
        authRepository.saveSessionId(user.session)
        authRepository.saveDDSServerIp(user.crewDdsServerIp)
        authRepository.saveDDSServerPort(user.crewDdsServerPort)
        authRepository.saveFileServerIp(user.crewChatFileServerIp)
        authRepository.saveFileServerPort(user.crewChatFileServerPort)
    }
}