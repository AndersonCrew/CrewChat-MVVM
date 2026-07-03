package com.crewcloud.apps.crewchat.domain.usecase

import com.crewcloud.apps.crewchat.domain.model.User
import com.crewcloud.apps.crewchat.domain.repository.AuthRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import javax.inject.Inject
import com.crewcloud.apps.crewchat.domain.model.Result
import com.crewcloud.apps.crewchat.domain.repository.UserRepository
import kotlinx.coroutines.coroutineScope

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
        password: String
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

        val user = (resultLogin as Result.ResultSuccess).result
        userRepository.saveUser(user)
        authRepository.saveSessionId(user.session)
        authRepository.saveDDSServerIp(user.crewDdsServerIp)
        authRepository.saveDDSServerPort(user.crewDdsServerPort)
        authRepository.saveFileServerIp(user.crewChatFileServerIp)
        authRepository.saveFileServerPort(user.crewChatFileServerPort)
        return@coroutineScope Result.ResultSuccess(result = user)
    }

    fun cleanDomain(rawDomain: String): String {
        return rawDomain
            .trim()
            .removePrefix("https://")
            .removePrefix("http://")
            .removeSuffix("/")
    }
}