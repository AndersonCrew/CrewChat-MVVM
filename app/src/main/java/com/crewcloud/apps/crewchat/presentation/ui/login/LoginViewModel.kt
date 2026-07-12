package com.crewcloud.apps.crewchat.presentation.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crewcloud.apps.crewchat.domain.model.Result
import com.crewcloud.apps.crewchat.domain.usecase.LoginUseCase
import com.crewcloud.apps.crewchat.presentation.core.model.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by BM Anderson on 2/7/26.
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : BaseViewModel() {
    private val _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState = _loginUiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<LoginEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun init() = viewModelScope.launch {
        val domain = loginUseCase.getDomain()
        val userName = loginUseCase.getUserName()
        val password = loginUseCase.getPassword()

        _loginUiState.update {
            it.copy(domain = domain ?: "", userName = userName ?: "", password = password ?: "")
        }
    }

    fun onDomainChanged(value: String) {
        _loginUiState.update { it.copy(domain = value) }
    }

    fun onUserNameChanged(value: String) {
        _loginUiState.update { it.copy(userName = value) }
    }

    fun onPasswordChanged(value: String) {
        _loginUiState.update { it.copy(password = value) }
    }

    fun onLogin(androidId: String) = viewModelScope.launch {
        val domain = _loginUiState.value.domain
        val userName = _loginUiState.value.userName
        val password = _loginUiState.value.password

        if (_loginUiState.value.isEmpty) {
            _uiEvent.emit(LoginEvent.LoginError("Please enter your information"))
        } else {
            try {
                _uiState.update { it.copy(isLoading = true) }

                when (val result =
                    loginUseCase(domain = domain, userName = userName, password = password, androidId = androidId)) {
                    is Result.Failure -> {
                        _uiEvent.emit(LoginEvent.LoginError(message = result.appError.error))
                    }

                    is Result.ResultSuccess -> {
                        _uiEvent.emit(LoginEvent.LoginSuccess)
                    }
                }
            } catch (e: Throwable) {
                _uiEvent.emit(LoginEvent.LoginError(message = e.message ?: ""))
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }

        }
    }
}