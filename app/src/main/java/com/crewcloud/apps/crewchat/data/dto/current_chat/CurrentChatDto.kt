package com.crewcloud.apps.crewchat.data.dto.current_chat

import com.crewcloud.apps.crewchat.data.dto.base.EmptyDTO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by BM Anderson on 12/7/26.
 */
@Serializable
data class CurrentChatDto(
    @SerialName("RoomNo") val roomNo: Int? = null,
    @SerialName("MakeUserNo") val makeUserNo: Int? = null,
    @SerialName("ModDate") val modDate: String? = null,
    @SerialName("strModDate") val strModDate: String? = null,
    @SerialName("IsOne") val isOne: Boolean? = null,
    @SerialName("RoomTitle") val roomTitle: String? = null,
    @SerialName("LastedMsgNo") val lastedMsgNo: Int? = null,
    @SerialName("LastedMsg") val lastedMsg: String? = null,
    @SerialName("LastedMsgDate") val lastedMsgDate: String? = null,
    @SerialName("strLastedMsgDate") val strLastedMsgDate: String? = null,
    @SerialName("LastedMsgType") val lastedMsgType: Int? = null,
    @SerialName("LastedMsgAttachType") val lastedMsgAttachType: Int? = null,
    @SerialName("LastedMsgAttachNo") val lastedMsgAttachNo: Int? = null,
    @SerialName("UnReadCount") val unreadCount: Int? = null,
    @SerialName("UserNos") val userNos: List<Int>? = emptyList(),
    @SerialName("UserList") val userList: List<EmptyDTO>? = emptyList(),
    @SerialName("Notification") val notification: Boolean? = null,
    @SerialName("Favorite") val favorite: Boolean? = null,
    @SerialName("LastedMsgAttachName") val lastedMsgAttachName: String? = null,
    @SerialName("MsgUserNo") val msgUserNo: Int? = null,
    @SerialName("RoomType") val roomType: Int? = null,
    @SerialName("GroupType") val groupType: Int? = null,
    @SerialName("NewRoom") val newRoom: Boolean? = null
)
