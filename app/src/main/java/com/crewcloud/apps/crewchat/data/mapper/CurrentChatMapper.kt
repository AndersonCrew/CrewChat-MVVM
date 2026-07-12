package com.crewcloud.apps.crewchat.data.mapper

import com.crewcloud.apps.crewchat.data.dto.current_chat.CurrentChatDto
import com.crewcloud.apps.crewchat.data.local.entity.CurrentChatEntity
import com.crewcloud.apps.crewchat.domain.model.CurrentChat

/**
 * Created by BM Anderson on 12/7/26.
 */
fun CurrentChatDto.toDomain(): CurrentChat {
    return CurrentChat(
        roomNo = roomNo ?: 0,
        makeUserNo = makeUserNo ?: 0,
        modDate = modDate ?: "",
        strModDate = strModDate ?: "",
        isOne = isOne ?: false,
        roomTitle = roomTitle ?: "",
        lastedMsgNo = lastedMsgNo ?: 0,
        lastedMsg = lastedMsg ?: "",
        lastedMsgDate = lastedMsgDate ?: "",
        strLastedMsgDate = strLastedMsgDate ?: "",
        lastedMsgType = lastedMsgType ?: 0,
        lastedMsgAttachType = lastedMsgAttachType ?: 0,
        lastedMsgAttachNo = lastedMsgAttachNo ?: 0,
        unreadCount = unreadCount ?: 0,
        userNos = userNos.orEmpty(),
        notification = notification ?: true,
        favorite = favorite ?: false,
        lastedMsgAttachName = lastedMsgAttachName ?: "",
        msgUserNo = msgUserNo ?: 0,
        roomType = roomType ?: 0,
        groupType = groupType ?: 0,
        newRoom = newRoom ?: false
    )
}

fun CurrentChatDto.toEntity(): CurrentChatEntity {
    return CurrentChatEntity(
        roomNo = roomNo ?: 0,
        makeUserNo = makeUserNo ?: 0,
        modDate = modDate ?: "",
        strModDate = strModDate ?: "",
        isOne = isOne ?: false,
        roomTitle = roomTitle ?: "",
        lastedMsgNo = lastedMsgNo ?: 0,
        lastedMsg = lastedMsg ?: "",
        lastedMsgDate = lastedMsgDate ?: "",
        strLastedMsgDate = strLastedMsgDate ?: "",
        lastedMsgType = lastedMsgType ?: 0,
        lastedMsgAttachType = lastedMsgAttachType ?: 0,
        lastedMsgAttachNo = lastedMsgAttachNo ?: 0,
        unreadCount = unreadCount ?: 0,
        userNos = userNos.toStorageString(),
        notification = notification ?: true,
        favorite = favorite ?: false,
        lastedMsgAttachName = lastedMsgAttachName ?: "",
        msgUserNo = msgUserNo ?: 0,
        roomType = roomType ?: 0,
        groupType = groupType ?: 0,
        newRoom = newRoom ?: false
    )
}

fun CurrentChatEntity.toDomain(): CurrentChat {
    return CurrentChat(
        roomNo = roomNo,
        makeUserNo = makeUserNo,
        modDate = modDate,
        strModDate = strModDate,
        isOne = isOne,
        roomTitle = roomTitle,
        lastedMsgNo = lastedMsgNo,
        lastedMsg = lastedMsg,
        lastedMsgDate = lastedMsgDate,
        strLastedMsgDate = strLastedMsgDate,
        lastedMsgType = lastedMsgType,
        lastedMsgAttachType = lastedMsgAttachType,
        lastedMsgAttachNo = lastedMsgAttachNo,
        unreadCount = unreadCount,
        userNos = userNos.toUserNoList(),
        notification = notification,
        favorite = favorite,
        lastedMsgAttachName = lastedMsgAttachName,
        msgUserNo = msgUserNo,
        roomType = roomType,
        groupType = groupType,
        newRoom = newRoom
    )
}

fun CurrentChat.toEntity(): CurrentChatEntity {
    return CurrentChatEntity(
        roomNo = roomNo,
        makeUserNo = makeUserNo,
        modDate = modDate,
        strModDate = strModDate,
        isOne = isOne,
        roomTitle = roomTitle,
        lastedMsgNo = lastedMsgNo,
        lastedMsg = lastedMsg,
        lastedMsgDate = lastedMsgDate,
        strLastedMsgDate = strLastedMsgDate,
        lastedMsgType = lastedMsgType,
        lastedMsgAttachType = lastedMsgAttachType,
        lastedMsgAttachNo = lastedMsgAttachNo,
        unreadCount = unreadCount,
        userNos = userNos.toStorageString(),
        notification = notification,
        favorite = favorite,
        lastedMsgAttachName = lastedMsgAttachName,
        msgUserNo = msgUserNo,
        roomType = roomType,
        groupType = groupType,
        newRoom = newRoom
    )
}

private fun List<Int>?.toStorageString(): String {
    return orEmpty().joinToString(",")
}

private fun String.toUserNoList(): List<Int> {
    if (isBlank()) return emptyList()
    return split(",").mapNotNull { it.trim().toIntOrNull() }
}
