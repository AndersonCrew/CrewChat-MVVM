package com.crewcloud.apps.crewchat.data.dto.chatting

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by BM Anderson on 12/7/26.
 */
@Serializable
data class ChattingDto(
    @SerialName("RoomNo") val roomNo: Long? = null,
    @SerialName("MakeUserNo") val makeUserNo: Int? = null,
    @SerialName("Moddate") val modDate: String? = null,
    @SerialName("IsOne") val isOne: Boolean? = null,
    @SerialName("RoomTitle") val roomTitle: String? = null,
    @SerialName("LastedMsg") val lastedMsg: String? = null,
    @SerialName("LastedMsgAttachType") val lastedMsgAttachType: Int? = null,
    @SerialName("LastedMsgType") val lastedMsgType: Int? = null,
    @SerialName("LastedMsgDate") val lastedMsgDate: String? = null,
    @SerialName("UserNos") val userNos: List<Int>? = emptyList(),
    @SerialName("WriterUser") val writerUser: Int? = null,
    @SerialName("WriteUserNo") val writerUserNo: Int? = null,
    @SerialName("MessageNo") val messageNo: Long? = null,
    @SerialName("UserNo") val userNo: Int? = null,
    @SerialName("MsgUserNo") val msgUserNo: Int? = null,
    @SerialName("Message") val message: String? = null,
    @SerialName("Type") val type: Int? = null,
    @SerialName("AttachNo") val attachNo: Int? = null,
    @SerialName("RegDate") val regDate: String? = null,
    @SerialName("strRegDate") val strRegDate: String? = null,
    @SerialName("UnReadCount") val unreadCount: Int? = null,
    @SerialName("AttachInfo") val attachInfo: AttachInfoDto? = null,
    @SerialName("AttachFileName") val attachFileName: String? = null,
    @SerialName("AttachFileType") val attachFileType: Int? = null,
    @SerialName("AttachFilePath") val attachFilePath: String? = null,
    @SerialName("AttachFileSize") val attachFileSize: Int? = null,
    @SerialName("UnreadTotalCount") val unreadTotalCount: Int? = null,
    @SerialName("RoomType") val roomType: Int? = null,
    @SerialName("Notification") val notification: Boolean? = null,
    @SerialName("Favorite") val favorite: Boolean? = null
)

@Serializable
data class AttachInfoDto(
    @SerialName("AttachNo") val attachNo: Int? = null,
    @SerialName("FileName") val fileName: String? = null,
    @SerialName("Type") val type: Int? = null,
    @SerialName("FullPath") val fullPath: String? = null,
    @SerialName("Size") val size: Int? = null
)
