package com.crewcloud.apps.crewchat.data.repository

import com.crewcloud.apps.crewchat.data.dto.chatting.ChattingGetType
import com.crewcloud.apps.crewchat.data.dto.chatting.ChattingRequest
import com.crewcloud.apps.crewchat.data.dto.current_chat.CurrentChatRequest
import com.crewcloud.apps.crewchat.data.local.SecureLocalStorage
import com.crewcloud.apps.crewchat.data.mapper.toDomain
import com.crewcloud.apps.crewchat.data.network.api.DazoneApiService
import com.crewcloud.apps.crewchat.data.network.safeApiCall
import com.crewcloud.apps.crewchat.domain.model.Chatting
import com.crewcloud.apps.crewchat.domain.model.CurrentChat
import com.crewcloud.apps.crewchat.domain.model.Result
import com.crewcloud.apps.crewchat.domain.repository.ChattingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by BM Anderson on 12/7/26.
 */
class ChattingRepositoryImpl @Inject constructor(
    private val api: DazoneApiService,
    private val secureLocalStorage: SecureLocalStorage
) : ChattingRepository {
    override fun getCurrentChatList(): Flow<Result<List<CurrentChat>>> = flow {
        val request = CurrentChatRequest(
            sessionId = secureLocalStorage.getSessionId() ?: "",
        )

        val result = safeApiCall { api.getCurrentChatList(request) }
        emit(
            when (result) {
                is Result.Failure -> result
                is Result.ResultSuccess -> Result.ResultSuccess(
                    result = result.result.d.data.map { it.toDomain() }
                )
            })
    }.flowOn(Dispatchers.IO)

    override suspend fun getChatList(request: ChattingRequest): Result<List<Chatting>> {
        return withContext(Dispatchers.IO) {
            val result = safeApiCall { api.getChatList(request) }
            when (result) {
                is Result.Failure -> result
                is Result.ResultSuccess -> Result.ResultSuccess(
                    result = result.result.d.data.map { it.toDomain() }
                )
            }
        }
    }
}
