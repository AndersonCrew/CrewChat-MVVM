package com.crewcloud.apps.crewchat.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.crewcloud.apps.crewchat.data.dto.chatting.ChattingGetType
import com.crewcloud.apps.crewchat.data.dto.chatting.ChattingRequest
import com.crewcloud.apps.crewchat.data.local.SecureLocalStorage
import com.crewcloud.apps.crewchat.data.local.dao.ChattingDao
import com.crewcloud.apps.crewchat.data.local.entity.ChattingEntity
import com.crewcloud.apps.crewchat.data.mapper.toEntity
import com.crewcloud.apps.crewchat.domain.model.Result
import com.crewcloud.apps.crewchat.domain.repository.ChattingRepository

/**
 * Created by BM Anderson on 12/7/26.
 */
@OptIn(ExperimentalPagingApi::class)
class ChattingRemoteMediator(
    private val roomNo: Int,
    private val secureLocalStorage: SecureLocalStorage,
    private val chattingDao: ChattingDao,
    private val chattingRepository: ChattingRepository
) : RemoteMediator<Int, ChattingEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ChattingEntity>
    ): MediatorResult {
         return try {
             val lastMessage = chattingDao.lastMessage(roomNo)
            val request = when(loadType) {
                LoadType.REFRESH -> {
                    ChattingRequest.create(
                        sessionId = secureLocalStorage.getSessionId()?: "",
                        roomNo = roomNo,
                        getType = if (lastMessage == null) {
                            ChattingGetType.FIRST
                        } else {
                            ChattingGetType.AFTER
                        },
                        baseDate = lastMessage?.strRegDate ?: "/Date(${System.currentTimeMillis()})/"
                    )
                }
                LoadType.PREPEND -> {
                    val firstMessage = chattingDao.firstMessage(roomNo)
                        ?: return MediatorResult.Success(endOfPaginationReached = true)

                    ChattingRequest.create(
                        sessionId = secureLocalStorage.getSessionId().orEmpty(),
                        roomNo = roomNo,
                        getType = ChattingGetType.BEFORE,
                        baseDate = firstMessage.strRegDate
                    )
                }
                LoadType.APPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
            }

             when(val response = chattingRepository.getChatList(request)) {
                 is Result.Failure -> MediatorResult.Error(Exception(response.appError.error))
                 is Result.ResultSuccess -> {
                     val messages = response.result.map { it.toEntity() }
                     chattingDao.upSertAll(messages)
                     MediatorResult.Success(
                         endOfPaginationReached = messages.isEmpty()
                     )
                 }
             }
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

}