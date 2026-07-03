package com.crewcloud.apps.crewchat.presentation.ui.login

/**
 * Created by BM Anderson on 3/7/26.
 */
sealed interface LoginEvent {
    class LoginError(val message: String): LoginEvent
    object LoginSuccess: LoginEvent
}