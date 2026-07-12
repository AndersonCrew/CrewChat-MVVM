package com.crewcloud.apps.crewchat.data.repository

import com.crewcloud.apps.crewchat.data.dto.base.timeToStringNotAMPM
import com.crewcloud.apps.crewchat.data.dto.check_api.CheckApiRequest
import com.crewcloud.apps.crewchat.data.dto.check_device_access.CheckDeviceAccessRequest
import com.crewcloud.apps.crewchat.data.dto.check_ssl.CheckSSLRequest
import com.crewcloud.apps.crewchat.data.dto.insert_fcm.NotificationOptions
import com.crewcloud.apps.crewchat.data.dto.insert_fcm.UpdateNotificationRequest
import com.crewcloud.apps.crewchat.data.dto.insert_fcm.WrapperNotificationOptions
import com.crewcloud.apps.crewchat.data.dto.login.LoginRequest
import com.crewcloud.apps.crewchat.data.local.AppPreferenceDataStore
import com.crewcloud.apps.crewchat.data.local.SecureLocalStorage
import com.crewcloud.apps.crewchat.data.mapper.toDomain
import com.crewcloud.apps.crewchat.data.network.api.DazoneApiService
import com.crewcloud.apps.crewchat.data.network.api.StaticApiService
import com.crewcloud.apps.crewchat.data.network.safeApiCall
import com.crewcloud.apps.crewchat.domain.model.AppError
import com.crewcloud.apps.crewchat.domain.model.CheckApi
import com.crewcloud.apps.crewchat.domain.model.CheckSSL
import com.crewcloud.apps.crewchat.domain.model.Result
import com.crewcloud.apps.crewchat.domain.model.User
import com.crewcloud.apps.crewchat.domain.repository.AuthRepository
import kotlinx.serialization.json.Json
import javax.inject.Inject

/**
 * Created by BM Anderson on 2/7/26.
 */
class AuthRepositoryImpl @Inject constructor(
    private val secureLocalStorage: SecureLocalStorage,
    private val staticApiService: StaticApiService,
    private val dazoneApiService: DazoneApiService,
    private val dataStore: AppPreferenceDataStore,
) :
    AuthRepository {
    override suspend fun getSessionId(): String? {
        return secureLocalStorage.getSessionId()
    }

    override suspend fun loginV5(
        domain: String,
        userName: String,
        password: String
    ): Result<User> {
        val request = LoginRequest(
            companyDomain = domain,
            userID = userName,
            password = password
        )
        when (val result = safeApiCall { dazoneApiService.loginV5(request) }) {
            is Result.Failure -> return result
            is Result.ResultSuccess -> {
                if (!result.result.d.success) {
                    val error = result.result.d.error?.message ?: "Unknown Error"
                    return Result.Failure(appError = AppError(error = error))
                }

                val userDto = result.result.d.data
                return Result.ResultSuccess(result = userDto.toDomain())
            }
        }
    }

    override suspend fun loginCrewChat(
        domain: String,
        userName: String,
        password: String
    ): Result<User> {
        val request = LoginRequest(
            companyDomain = domain,
            userID = userName,
            password = password
        )

        when (val result = safeApiCall { dazoneApiService.loginCrewChat(request) }) {
            is Result.Failure -> return result
            is Result.ResultSuccess -> {
                if (!result.result.d.success) {
                    val error = result.result.d.error?.message ?: "Unknown Error"
                    return Result.Failure(appError = AppError(error = error))

                }

                val userDto = result.result.d.data
                return Result.ResultSuccess(result = userDto.toDomain())
            }
        }
    }

    override suspend fun checkSSL(domain: String): Result<CheckSSL> {
        return when (val result =
            safeApiCall { staticApiService.checkSSL(CheckSSLRequest(domain = domain)) }) {
            is Result.Failure -> result
            is Result.ResultSuccess -> Result.ResultSuccess(result = result.result.d.data.toDomain())
        }
    }

    override suspend fun checkLoginApi(domain: String): Result<CheckApi> {
        return when (val result =
            safeApiCall {
                staticApiService.checkApi(
                    CheckApiRequest(
                        domain = domain,
                        apiName = CheckApiRequest.LOGIN_CREWCHAT
                    )
                )
            }) {
            is Result.Failure -> result
            is Result.ResultSuccess -> Result.ResultSuccess(result = result.result.d.data.toDomain())
        }
    }

    override suspend fun checkApiDeviceAccess(domain: String): Result<CheckApi> {
        return when (val result =
            safeApiCall {
                staticApiService.checkApi(
                    CheckApiRequest(
                        domain = domain,
                        apiName = CheckApiRequest.MOBILE_DEVICES_ACCESS
                    )
                )
            }) {
            is Result.Failure -> result
            is Result.ResultSuccess -> Result.ResultSuccess(result = result.result.d.data.toDomain())
        }
    }

    override suspend fun checkDeviceAccess(androidId: String): Result<Boolean> {
        val request = CheckDeviceAccessRequest(
            sessionId = getSessionId() ?: "",
            mobileDeviceId = dataStore.getFCMToken() ?: "",
            mobileUUID = androidId
        )
        val result = safeApiCall { dazoneApiService.checkDevicesAccess(request) }

        return when (result) {
            is Result.Failure -> result
            is Result.ResultSuccess -> {
                if(!result.result.d.success) {
                    return Result.Failure(appError = AppError(error = result.result.d.error?.message ?: ""))
                }

                Result.ResultSuccess(result = true)
            }
        }
    }

    override suspend fun insertAndroidDevice(): Result<Boolean> {
        val enableNotification = dataStore.getEnableNotification()
        val enableSound = dataStore.getEnableSound()
        val enableVibrate = dataStore.getEnableVibrate()
        val enableTime = dataStore.getEnableTime()
        val isEnableNotificationWhenUsingPcVersion = dataStore.getEnableNotificationWhenUsingPC()

        val startHour = dataStore.getStartNotificationHour()
        val startMinute = dataStore.getStartNotificationMinutes()
        val endHour = dataStore.getEndNotificationHour()
        val endMinute = dataStore.getEndNotificationMinutes()

        val notificationOptions = NotificationOptions(
            enabled = enableNotification,
            sound = enableSound,
            vibrate = enableVibrate,
            notificationTime = enableTime,
            startTime = timeToStringNotAMPM(startHour, startMinute),
            endTime = timeToStringNotAMPM(endHour, endMinute),
        )

        val regJson = notificationOptions.copy(confirmOnline = isEnableNotificationWhenUsingPcVersion)
        val wrapperNotificationOptions = WrapperNotificationOptions(
            deviceID = dataStore.getFCMToken()?: "",
            notificationOptions = regJson
        )

        val request = UpdateNotificationRequest(
            sessionId = secureLocalStorage.getSessionId() ?: "",
            notificationOptions = Json.encodeToString(notificationOptions),
            reqJson = Json.encodeToString(wrapperNotificationOptions),
        )

        return when (val result = safeApiCall { dazoneApiService.insertAndroidDevice(request) }) {
            is Result.Failure -> result
            is Result.ResultSuccess -> {
                if(result.result.d.success) {
                    return Result.ResultSuccess(true)
                }

                Result.Failure(appError = AppError(error = "Insert Device Token Failure!"))
            }
        }
    }

    override suspend fun saveDomain(domain: String) {
        dataStore.saveDomain(domain)
    }

    override suspend fun saveUserName(userName: String) {
        secureLocalStorage.saveUserName(userName)
    }

    override suspend fun savePassword(password: String) {
        secureLocalStorage.savePassword(password)
    }

    override suspend fun saveBaseUrl(url: String) {
        dataStore.saveBaseUrl(url)
    }

    override suspend fun saveDDSServerIp(ip: String) {
        dataStore.saveDDSServerIP(ip)
    }

    override suspend fun saveFileServerIp(ip: String) {
        dataStore.saveFileServerIP(ip)
    }

    override suspend fun saveDDSServerPort(port: Int) {
        dataStore.saveDDSServerPort(port)
    }

    override suspend fun saveFileServerPort(port: Int) {
        dataStore.saveFileServerPort(port)
    }

    override suspend fun saveSessionId(sessionId: String) {
        secureLocalStorage.saveSessionId(sessionId)
    }

    override suspend fun getUserName(): String? {
        return secureLocalStorage.getUserName()
    }

    override suspend fun getPassword(): String? {
        return secureLocalStorage.getPassword()
    }

    override suspend fun clearSession() {
        secureLocalStorage.clearSession()
    }
}
