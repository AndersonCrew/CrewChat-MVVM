package com.crewcloud.apps.crewchat.presentation.core.model

/**
 * Created by BM Anderson on 6/7/26.
 */
data class BaseUiState(
    val isLoading: Boolean = false,
    val error: String?= null
)