package com.crewcloud.apps.crewchat.presentation.core.model

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Created by BM Anderson on 6/7/26.
 */

abstract class BaseViewModel: ViewModel() {
    protected val _uiState = MutableStateFlow(BaseUiState())
    val uiState = _uiState.asStateFlow()
}