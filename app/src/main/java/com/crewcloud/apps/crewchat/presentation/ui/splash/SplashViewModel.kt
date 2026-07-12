package com.crewcloud.apps.crewchat.presentation.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crewcloud.apps.crewchat.domain.repository.AuthRepository
import com.crewcloud.apps.crewchat.presentation.core.model.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by BM Anderson on 2/7/26.
 */
@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {

    private val _uiEvent = Channel<SplashEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun checkAuthentication() = viewModelScope.launch {
        delay(2000)
        if (authRepository.getSessionId().isNullOrEmpty()) {
            _uiEvent.send(SplashEvent.NavigateToLogin)
        } else {
            _uiEvent.send(SplashEvent.NavigateToHome)
        }
    }

    fun check() = viewModelScope.launch {
        launch {
            delay(1000)
            print("A")
        }

        launch { throw Exception() }
        print("Done")


    }
}