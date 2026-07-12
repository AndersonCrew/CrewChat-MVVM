package com.crewcloud.apps.crewchat.domain.repository

import com.crewcloud.apps.crewchat.data.dto.chatting.ChattingGetType
import com.crewcloud.apps.crewchat.data.dto.chatting.ChattingRequest
import com.crewcloud.apps.crewchat.domain.model.Chatting
import com.crewcloud.apps.crewchat.domain.model.CurrentChat
import com.crewcloud.apps.crewchat.domain.model.Result
import kotlinx.coroutines.flow.Flow

/**
 * Created by BM Anderson on 12/7/26.
 */
interface ChattingRepository {
    fun getCurrentChatList(): Flow<Result<List<CurrentChat>>>
    suspend fun getChatList(request: ChattingRequest): Result<List<Chatting>>
}