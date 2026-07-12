package com.crewcloud.apps.crewchat.data.mapper

import com.crewcloud.apps.crewchat.data.dto.chatting.ChattingDto
import com.crewcloud.apps.crewchat.data.local.entity.ChattingEntity
import com.crewcloud.apps.crewchat.domain.model.Chatting

/**
 * Created by BM Anderson on 12/7/26.
 */
fun ChattingDto.toDomain(): Chatting {
    val attachInfo = attachInfo
    return Chatting(
        roomNo = roomNo ?: 0L,
        makeUserNo = makeUserNo ?: 0,
        modDate = modDate ?: "",
        isOne = isOne ?: false,
        roomTitle = roomTitle ?: "",
        lastedMsg = lastedMsg ?: "",
        lastedMsgAttachType = lastedMsgAttachType ?: 0,
        lastedMsgType = lastedMsgType ?: 0,
        lastedMsgDate = lastedMsgDate ?: "",
        userNos = userNos.orEmpty(),
        writerUser = writerUser ?: 0,
        writerUserNo = writerUserNo ?: 0,
        messageNo = messageNo ?: 0L,
        userNo = userNo ?: 0,
        msgUserNo = msgUserNo ?: 0,
        message = message ?: "",
        type = type ?: 0,
        attachNo = attachInfo?.attachNo ?: attachNo ?: 0,
        regDate = regDate ?: "",
        strRegDate = strRegDate ?: "",
        unreadCount = unreadCount ?: 0,
        attachFileName = attachInfo?.fileName?.ifBlank { null } ?: attachFileName ?: "",
        attachFileType = attachInfo?.type ?: attachFileType ?: 0,
        attachFilePath = attachInfo?.fullPath?.ifBlank { null } ?: attachFilePath ?: "",
        attachFileSize = attachInfo?.size ?: attachFileSize ?: 0,
        unreadTotalCount = unreadTotalCount ?: 0,
        roomType = roomType ?: 0,
        notification = notification ?: true,
        favorite = favorite ?: false
    )
}

fun ChattingDto.toEntity(): ChattingEntity {
    return toDomain().toEntity()
}

fun ChattingEntity.toDomain(): Chatting {
    return Chatting(
        roomNo = roomNo,
        makeUserNo = makeUserNo,
        modDate = modDate,
        isOne = isOne,
        roomTitle = roomTitle,
        lastedMsg = lastedMsg,
        lastedMsgAttachType = lastedMsgAttachType,
        lastedMsgType = lastedMsgType,
        lastedMsgDate = lastedMsgDate,
        userNos = userNos.toUserNoList(),
        writerUser = writerUser,
        writerUserNo = writerUserNo,
        messageNo = messageNo,
        userNo = userNo,
        msgUserNo = msgUserNo,
        message = message,
        type = type,
        attachNo = attachNo,
        regDate = regDate,
        strRegDate = strRegDate,
        unreadCount = unreadCount,
        attachFileName = attachFileName,
        attachFileType = attachFileType,
        attachFilePath = attachFilePath,
        attachFileSize = attachFileSize,
        unreadTotalCount = unreadTotalCount,
        roomType = roomType,
        notification = notification,
        favorite = favorite,
        hasSent = hasSent,
        isHeader = isHeader,
        isSendTemp = isSendTemp
    )
}

fun Chatting.toEntity(): ChattingEntity {
    return ChattingEntity(
        messageNo = messageNo,
        roomNo = roomNo,
        makeUserNo = makeUserNo,
        modDate = modDate,
        isOne = isOne,
        roomTitle = roomTitle,
        lastedMsg = lastedMsg,
        lastedMsgAttachType = lastedMsgAttachType,
        lastedMsgType = lastedMsgType,
        lastedMsgDate = lastedMsgDate,
        userNos = userNos.toStorageString(),
        writerUser = writerUser,
        writerUserNo = writerUserNo,
        userNo = userNo,
        msgUserNo = msgUserNo,
        message = message,
        type = type,
        attachNo = attachNo,
        regDate = regDate,
        strRegDate = strRegDate,
        unreadCount = unreadCount,
        attachFileName = attachFileName,
        attachFileType = attachFileType,
        attachFilePath = attachFilePath,
        attachFileSize = attachFileSize,
        unreadTotalCount = unreadTotalCount,
        roomType = roomType,
        notification = notification,
        favorite = favorite,
        hasSent = hasSent,
        isHeader = isHeader,
        isSendTemp = isSendTemp
    )
}

private fun List<Int>.toStorageString(): String {
    return joinToString(",")
}

private fun String.toUserNoList(): List<Int> {
    if (isBlank()) return emptyList()
    return split(",").mapNotNull { it.trim().toIntOrNull() }
}
