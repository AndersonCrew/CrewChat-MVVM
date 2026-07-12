package com.crewcloud.apps.crewchat.presentation.ui.home.current_chat

import androidx.lifecycle.viewModelScope
import com.crewcloud.apps.crewchat.domain.model.CurrentChat
import com.crewcloud.apps.crewchat.domain.repository.ChattingRepository
import com.crewcloud.apps.crewchat.presentation.core.model.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.crewcloud.apps.crewchat.domain.model.Result
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

/**
 * Created by BM Anderson on 12/7/26.
 */
@HiltViewModel
class CurrentChatViewModel @Inject constructor(
    chattingRepository: ChattingRepository
) : BaseViewModel() {

    val currentChatRooms: StateFlow<List<CurrentChat>> = chattingRepository.getCurrentChatList()
        .onStart { _uiState.update { it.copy(isLoading = true) } }
        .map { result ->
            _uiState.update { it.copy(isLoading = false) }
            when (result) {
                is Result.ResultSuccess -> result.result
                is Result.Failure -> {
                    _uiState.update { it.copy(error = result.appError.error) }
                    emptyList()
                }
            }
        }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}