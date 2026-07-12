package com.crewcloud.apps.crewchat.presentation.ui.home.chatting

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import com.crewcloud.apps.crewchat.data.local.SecureLocalStorage
import com.crewcloud.apps.crewchat.data.local.dao.ChattingDao
import com.crewcloud.apps.crewchat.data.mapper.toDomain
import com.crewcloud.apps.crewchat.data.repository.ChattingRemoteMediator
import com.crewcloud.apps.crewchat.domain.repository.ChattingRepository
import com.crewcloud.apps.crewchat.domain.repository.UserRepository
import com.crewcloud.apps.crewchat.presentation.core.model.BaseViewModel
import com.crewcloud.apps.crewchat.presentation.core.navigation.ChattingScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * Created by BM Anderson on 12/7/26.
 */
@HiltViewModel
class ChattingViewModel @Inject constructor(
    saveStateHandler: SavedStateHandle,
    chattingRepository: ChattingRepository,
    userRepository: UserRepository,
    chattingDao: ChattingDao,
    secureLocalStorage: SecureLocalStorage
) : BaseViewModel() {
    private val args = saveStateHandler.toRoute<ChattingScreen>()
    val roomNo: Int = args.roomNo

    private val currentUserNo: StateFlow<Int> = flow {
        emit(userRepository.getUser()?.id ?: -1)
    }.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = -1
    )

    @OptIn(ExperimentalPagingApi::class)
    val chattingPagingFlow = Pager(
        config = PagingConfig(
            pageSize = 20,
            initialLoadSize = 20,
            enablePlaceholders = false
        ),
        initialKey = null,
        pagingSourceFactory = {
            chattingDao.pagingMessage(roomNo)
        },
        remoteMediator = ChattingRemoteMediator(
            roomNo = roomNo,
            secureLocalStorage = secureLocalStorage,
            chattingDao = chattingDao,
            chattingRepository = chattingRepository
        )
    ).flow.map {
        it.map {
            it.toDomain()
        }
    }

    val chatRows: Flow<PagingData<ChatRow>> =
        combine(chattingPagingFlow, currentUserNo) { pagingData, userNo ->
            pagingData
                .map { message ->
                    ChatRow.Message(
                        message = message,
                        isMine = message.isMine(userNo)
                    )
                }
                .insertSeparators { before, after ->
                    val afterMessage = after?.message ?: return@insertSeparators null
                    val beforeMessage = before?.message

                    if (beforeMessage?.dateKey() != afterMessage.dateKey()) {
                        ChatRow.DateHeader(text = afterMessage.displayDateHeader())
                    } else {
                        null
                    }
                }
        }.cachedIn(viewModelScope)
}
