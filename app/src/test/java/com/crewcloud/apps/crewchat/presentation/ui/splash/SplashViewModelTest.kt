package com.crewcloud.apps.crewchat.presentation.ui.splash

import app.cash.turbine.test
import com.crewcloud.apps.crewchat.MainDispatcherRule
import com.crewcloud.apps.crewchat.domain.repository.AuthRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Created by BM Anderson on 4/7/26.
 */
class SplashViewModelTest {
    private lateinit var viewModel: SplashViewModel
    private val authRepository: AuthRepository = mockk()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        viewModel = SplashViewModel(authRepository)
    }

    @Test
    fun checkHasAuthentication() = runTest {
        val sessionId = "abcd"
        coEvery { authRepository.getSessionId() } returns sessionId
        viewModel.uiEvent.test {
            viewModel.checkAuthentication()
            val actualEvent = awaitItem()
            assertThat(actualEvent).isInstanceOf(SplashEvent.NavigateToHome::class.java)
        }
    }

    @Test
    fun checkNonAuthentication() = runTest {
        val sessionId = null
        coEvery { authRepository.getSessionId() } returns sessionId
        viewModel.uiEvent.test {
            viewModel.checkAuthentication()
            val actualEvent = awaitItem()
            assertThat(actualEvent).isInstanceOf(SplashEvent.NavigateToLogin::class.java)
        }
    }
}