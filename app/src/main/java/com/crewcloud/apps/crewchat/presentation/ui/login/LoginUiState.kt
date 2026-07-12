package com.crewcloud.apps.crewchat.presentation.ui.login

import androidx.paging.LoadState

/**
 * Created by BM Anderson on 2/7/26.
 */
data class LoginUiState(
    val domain: String = "",
    val userName: String = "",
    val password: String = "",
) {
    val isEmpty: Boolean = domain.isEmpty() || userName.isEmpty() || password.isEmpty()
}
