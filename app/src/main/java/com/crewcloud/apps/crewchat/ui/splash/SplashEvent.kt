package com.crewcloud.apps.crewchat.ui.splash

/**
 * Created by BM Anderson on 2/7/26.
 */
sealed interface SplashEvent {
    object NavigateToLogin: SplashEvent
    object NavigateToHome: SplashEvent
}