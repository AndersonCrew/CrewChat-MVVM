package com.crewcloud.apps.crewchat.domain

import android.util.Log
import com.crewcloud.apps.crewchat.domain.model.AppError
import com.crewcloud.apps.crewchat.domain.model.CheckApi
import com.crewcloud.apps.crewchat.domain.model.CheckSSL
import com.crewcloud.apps.crewchat.domain.model.Result
import com.crewcloud.apps.crewchat.domain.model.User
import com.crewcloud.apps.crewchat.domain.repository.AuthRepository
import com.crewcloud.apps.crewchat.domain.repository.UserRepository
import com.crewcloud.apps.crewchat.domain.usecase.LoginUseCase
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

/**
 * Created by BM Anderson on 4/7/26.
 */

class LoginUseCaseTest {
    private lateinit var loginUseCase: LoginUseCase
    private val authRepository = mockk<AuthRepository>()
    private val userRepository = mockk<UserRepository>()


    @Before
    fun setUp() {
        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
        loginUseCase = LoginUseCase(authRepository, userRepository)
    }

    @Test
    fun cleanDomain_inputHttpAndSpace_returnCleanDomain() {
        val result = loginUseCase.cleanDomain("https://crewcloud.com/   ")
        assertThat(result).isEqualTo("crewcloud.com")
    }

    @Test
    fun getDomain_returnValueFromUserRepository() = runTest {
        coEvery { userRepository.getDomain() } returns TEST_DOMAIN

        val result = loginUseCase.getDomain()
        assertThat(result).isEqualTo(TEST_DOMAIN)
    }


    @Test
    fun getUserName_returnValueFromAuthRepository() = runTest {
        coEvery { authRepository.getUserName() } returns TEST_USERNAME

        val result = loginUseCase.getUserName()
        assertThat(result).isEqualTo(TEST_USERNAME)
    }

    @Test
    fun getPassword_returnValueFromAuthRepository() = runTest {
        coEvery { authRepository.getPassword() } returns TEST_PASSWORD

        val result = loginUseCase.getPassword()
        assertThat(result).isEqualTo(TEST_PASSWORD)
    }

    private fun mockInitialSaveSuccess() {
        coEvery { authRepository.saveDomain(TEST_DOMAIN) } returns Unit
        coEvery { authRepository.saveUserName(TEST_USERNAME) } returns Unit
        coEvery { authRepository.savePassword(TEST_PASSWORD) } returns Unit
    }

    private fun mockCheckSSL(value: Boolean) {
        val mockResult =
            if (value) Result.ResultSuccess(result = CheckSSL(ssl = true)) else Result.Failure(
                appError = AppError(error = "Error")
            )

        coEvery { authRepository.checkSSL(TEST_DOMAIN) } returns mockResult
        coEvery { authRepository.saveBaseUrl(TEST_BASE_URL) } returns Unit
    }

    private fun mockCheckLoginApi(value: Boolean, result: Boolean) {
        val mockResult =
            if (value) Result.ResultSuccess(result = CheckApi(api = result)) else Result.Failure(
                appError = AppError(error = "Error")
            )

        coEvery { authRepository.checkLoginApi(TEST_DOMAIN) } returns mockResult
    }

    @Test
    fun checkSSL_returnFailure() = runTest {
        mockInitialSaveSuccess()
        mockCheckSSL(false)

        val result = loginUseCase.invoke(TEST_DOMAIN, TEST_USERNAME, TEST_PASSWORD, TEST_ANDROID_ID)
        assertThat(result).isInstanceOf(Result.Failure::class.java)

        coVerify(exactly = 1) { authRepository.saveDomain(TEST_DOMAIN) }
        coVerify(exactly = 1) { authRepository.saveUserName(TEST_USERNAME) }
        coVerify(exactly = 1) { authRepository.savePassword(TEST_PASSWORD) }
        coVerify(exactly = 0) { authRepository.checkLoginApi(TEST_DOMAIN) }
    }

    @Test
    fun checkSSL_returnSuccess_checkLoginApi_returnFailure() = runTest {
        mockInitialSaveSuccess()
        mockCheckSSL(true)
        mockCheckLoginApi(value = false, result = false)
        val result = loginUseCase(TEST_DOMAIN, TEST_USERNAME, TEST_PASSWORD, TEST_ANDROID_ID)
        coVerify(exactly = 1) { authRepository.saveBaseUrl(TEST_BASE_URL) }
        assertThat(result).isInstanceOf(Result.Failure::class.java)
    }

    @Test
    fun checkSSL_returnSuccess_checkLoginApi_returnSuccessWithResultTrue_returnFailure() = runTest {

        //Arrange
        val mockResult = Result.Failure(appError = AppError(error = ""))
        mockInitialSaveSuccess()
        mockCheckSSL(true)
        mockCheckLoginApi(value = true, result = true)
        coEvery {
            authRepository.loginCrewChat(
                TEST_DOMAIN,
                TEST_USERNAME,
                TEST_PASSWORD
            )
        } returns mockResult

        //Act
        val result = loginUseCase(TEST_DOMAIN, TEST_USERNAME, TEST_PASSWORD, TEST_ANDROID_ID)

        //Assert
        assertThat(result).isInstanceOf(Result.Failure::class.java)
        coVerify(exactly = 1) {
            authRepository.loginCrewChat(
                TEST_DOMAIN,
                TEST_USERNAME,
                TEST_PASSWORD
            )
        }

        coVerify(exactly = 0) {
            authRepository.loginV5(
                TEST_DOMAIN,
                TEST_USERNAME,
                TEST_PASSWORD
            )
        }

    }

    @Test
    fun checkSSL_returnSuccess_checkLoginApi_returnSuccessWithResultFalse_returnFailure() = runTest {

        //Arrange
        val mockResult = Result.Failure(appError = AppError(error = ""))
        mockInitialSaveSuccess()
        mockCheckSSL(true)
        mockCheckLoginApi(value = true, result = false)
        coEvery {
            authRepository.loginV5(
                TEST_DOMAIN,
                TEST_USERNAME,
                TEST_PASSWORD
            )
        } returns mockResult

        //Act
        val result = loginUseCase(TEST_DOMAIN, TEST_USERNAME, TEST_PASSWORD, TEST_ANDROID_ID)

        //Assert
        assertThat(result).isInstanceOf(Result.Failure::class.java)
        coVerify(exactly = 0) {
            authRepository.loginCrewChat(
                TEST_DOMAIN,
                TEST_USERNAME,
                TEST_PASSWORD
            )
        }

        coVerify(exactly = 1) {
            authRepository.loginV5(
                TEST_DOMAIN,
                TEST_USERNAME,
                TEST_PASSWORD
            )
        }

    }

    fun stubPersisUser(user: User) {
        coEvery { userRepository.saveUser(user) } returns Unit
        coEvery { authRepository.saveSessionId(user.session) } returns Unit
        coEvery { authRepository.saveDDSServerIp(user.crewDdsServerIp) } returns Unit
        coEvery { authRepository.saveFileServerIp(user.crewChatFileServerIp) } returns Unit
        coEvery { authRepository.saveDDSServerPort(user.crewDdsServerPort) } returns Unit
        coEvery { authRepository.saveFileServerPort(user.crewChatFileServerPort) } returns Unit
        coEvery { authRepository.checkApiDeviceAccess(TEST_DOMAIN) } returns Result.ResultSuccess(CheckApi(api = false))
        coEvery { authRepository.insertAndroidDevice() } returns Result.ResultSuccess(true)
    }

    @Test
    fun checkSSL_returnSuccess_checkLoginApi_returnSuccessWithResultTrue_returnSuccess() = runTest {

        // Arrange
        val  user = createTestUser()
        val mockResult = Result.ResultSuccess(result = user)
        mockInitialSaveSuccess()
        mockCheckSSL(true)
        mockCheckLoginApi(value = true, result = true)
        stubPersisUser(user)
        coEvery {
            authRepository.loginCrewChat(
                TEST_DOMAIN,
                TEST_USERNAME,
                TEST_PASSWORD
            )
        } returns mockResult

        //Act
        val result = loginUseCase(TEST_DOMAIN, TEST_USERNAME, TEST_PASSWORD, TEST_ANDROID_ID)
        coVerify(exactly = 1) { userRepository.saveUser(user) }
        coVerify(exactly = 1) { authRepository.saveSessionId(user.session) }
        coVerify(exactly = 1) { authRepository.saveDDSServerIp(user.crewDdsServerIp) }
        coVerify(exactly = 1) { authRepository.saveFileServerIp(user.crewChatFileServerIp) }
        coVerify(exactly = 1) { authRepository.saveDDSServerPort(user.crewDdsServerPort) }
        coVerify(exactly = 1) { authRepository.saveFileServerPort(user.crewChatFileServerPort) }

        assertThat(result).isInstanceOf(Result.ResultSuccess::class.java)
    }


    @Test
    fun checkSSL_returnSuccess_checkLoginApi_returnSuccessWithResultFalse_returnSuccess() = runTest {

        //Arrange
        val user = createTestUser()

        val mockResult = Result.ResultSuccess(result = user)
        mockInitialSaveSuccess()
        mockCheckSSL(true)
        mockCheckLoginApi(value = true, result = false)
        stubPersisUser(user)
        coEvery {
            authRepository.loginV5(
                TEST_DOMAIN,
                TEST_USERNAME,
                TEST_PASSWORD
            )
        } returns mockResult

        //Act
        val result = loginUseCase(TEST_DOMAIN, TEST_USERNAME, TEST_PASSWORD, TEST_ANDROID_ID)
        assertThat(result).isInstanceOf(Result.ResultSuccess::class.java)
    }

    private fun createTestUser(
        session: String = "mock_session_123",
        ddsIp: String = "192.168.1.1",
        ddsPort: String = "8080"
    ): User {
        return User(
            session = session,
            crewDdsServerIp = ddsIp,
            crewDdsServerPort = 1,
            crewChatFileServerIp = "192.168.1.2",
            crewChatFileServerPort = 1,
            userId = "1",
            fullName = "Anderson",
            id = 1,
            avatarUrl = "url",
            permissionType = 0,
            companyName = "dazone",
            mailAddress = "anhtam@gmail.com",
            companyLocations = listOf(),
            companyNo = 0,
        )
    }

    companion object {
        private const val TEST_BASE_URL = "https://crewcloud.com"
        private const val TEST_DOMAIN = "crewcloud.com"
        private const val TEST_USERNAME = "dazone"
        private const val TEST_PASSWORD = "123456"
        private const val TEST_ANDROID_ID = "android-test-id"
    }
}