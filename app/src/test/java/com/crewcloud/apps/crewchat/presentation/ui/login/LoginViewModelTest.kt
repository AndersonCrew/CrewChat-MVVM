package com.crewcloud.apps.crewchat.presentation.ui.login

import app.cash.turbine.test
import com.crewcloud.apps.crewchat.MainDispatcherRule
import com.crewcloud.apps.crewchat.domain.model.Result
import com.crewcloud.apps.crewchat.domain.model.User
import com.crewcloud.apps.crewchat.domain.usecase.LoginUseCase
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.async
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Created by BM Anderson on 4/7/26.
 */
class LoginViewModelTest {
    private lateinit var viewModel: LoginViewModel
    private val loginUseCase: LoginUseCase = mockk()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        viewModel = LoginViewModel(loginUseCase)
    }

    @Test
    fun checkInitValueFromUseCase() = runTest {
        val domain = "dazone.com"
        val userName = "anderson"
        val password = "123456"

        viewModel.loginUiState.test {
            val initialState = awaitItem()
            assertThat(initialState).isEqualTo(LoginUiState())

            coEvery { loginUseCase.getDomain() } returns domain
            coEvery { loginUseCase.getUserName() } returns userName
            coEvery { loginUseCase.getPassword() } returns password

            viewModel.init()
            val uiState = awaitItem()
            assertThat(uiState.domain).isEqualTo(domain)
            assertThat(uiState.userName).isEqualTo(userName)
            assertThat(uiState.password).isEqualTo(password)
        }
    }

    @Test
    fun testOnDomainChanged() = runTest {
        val value = "dazone"
        viewModel.loginUiState.test {
            val initialState = awaitItem()
            assertThat(initialState.domain).isEmpty()

            viewModel.onDomainChanged(value)
            val updatedState = awaitItem()
            assertThat(updatedState.domain).isEqualTo(value)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun testOnUserNameChanged() = runTest {
        val value = "anderson"
        viewModel.loginUiState.test {
            val initialState = awaitItem()
            assertThat(initialState.userName).isEmpty()

            viewModel.onUserNameChanged(value)
            val updatedState = awaitItem()
            assertThat(updatedState.userName).isEqualTo(value)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun testOnPasswordChanged() = runTest {
        val value = "123456"
        viewModel.loginUiState.test {
            val initialState = awaitItem()
            assertThat(initialState.password).isEmpty()

            viewModel.onPasswordChanged(value)
            val updatedState = awaitItem()
            assertThat(updatedState.password).isEqualTo(value)

            cancelAndIgnoreRemainingEvents()
        }
    }

    fun stubGetValueFromUseCase(domain: String?, userName: String?, password: String?) {
        coEvery { loginUseCase.getDomain() } returns domain
        coEvery { loginUseCase.getUserName() } returns userName
        coEvery { loginUseCase.getPassword() } returns password
    }


    @Test
    fun testLoginIsNotFullField_returnLoginErrorEvent() = runTest {
        val domain = null
        val password = "123456"
        val userName = "anderson"

        stubGetValueFromUseCase(domain, userName, password)

        viewModel.uiEvent.test {
            viewModel.onLogin(TEST_ANDROID_ID)
            val uiEvent = awaitItem()
            assertThat(uiEvent).isInstanceOf(LoginEvent.LoginError::class.java)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun testLoginFullField_returnFailure() = runTest {
        val domain = "dazone.com"
        val password = "123456"
        val userName = "anderson"
        val mockResult = Result.Failure(mockk(relaxed = true))

        stubGetValueFromUseCase(domain, userName, password)
        coEvery { loginUseCase.invoke(domain, userName, password, TEST_ANDROID_ID) } returns mockResult
        viewModel.init()

        viewModel.uiState.test stateBlock@{
            viewModel.uiEvent.test eventBlock@{
                val initialState = this@stateBlock.awaitItem()
                assertThat(initialState.isLoading).isFalse()


                val loginStateDeferred = async { this@stateBlock.awaitItem() }
                val eventDeferred = async { this@eventBlock.awaitItem() }

                viewModel.onLogin(TEST_ANDROID_ID)

                val loginState = loginStateDeferred.await()
                val event = eventDeferred.await()

                assertThat(loginState.isLoading).isTrue()
                assertThat(event).isInstanceOf(LoginEvent.LoginError::class.java)

                val finalState = this@stateBlock.awaitItem()
                assertThat(finalState.isLoading).isFalse()

                this@eventBlock.cancelAndIgnoreRemainingEvents()
            }

            this@stateBlock.cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun testLoginHappyCase() = runTest {
        val domain = "dazone.com"
        val password = "123456"
        val userName = "anderson"
        val mockResult = Result.ResultSuccess<User>(result = mockk())

        stubGetValueFromUseCase(domain, userName, password)
        coEvery { loginUseCase.invoke(domain, userName, password, TEST_ANDROID_ID) } returns mockResult
        viewModel.init()

        viewModel.uiState.test stateBlock@{
            viewModel.uiEvent.test eventBlock@{
                val initialState = this@stateBlock.awaitItem()
                assertThat(initialState.isLoading).isFalse()

                val loginStateDeferred = async { this@stateBlock.awaitItem() }
                val loginEventDeferred = async { this@eventBlock.awaitItem() }

                viewModel.onLogin(TEST_ANDROID_ID)
                val loadingState = this@stateBlock.awaitItem()
                assertThat(loadingState.isLoading).isTrue()

                val loginState = loginStateDeferred.await()
                val loginEvent = loginEventDeferred.await()

                assertThat(loginState.isLoading).isFalse()
                assertThat(loginEvent).isInstanceOf(LoginEvent.LoginSuccess::class.java)
            }
        }
    }
    companion object {
        private const val TEST_ANDROID_ID = "android-test-id"
    }
}
