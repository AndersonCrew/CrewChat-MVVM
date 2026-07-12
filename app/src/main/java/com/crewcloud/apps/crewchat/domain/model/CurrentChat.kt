package com.crewcloud.apps.crewchat.domain.model

/**
 * Created by BM Anderson on 12/7/26.
 */
data class CurrentChat(
    val roomNo: Int,
    val makeUserNo: Int,
    val modDate: String,
    val strModDate: String,
    val isOne: Boolean,
    val roomTitle: String,
    val lastedMsgNo: Int,
    val lastedMsg: String,
    val lastedMsgDate: String,
    val strLastedMsgDate: String,
    val lastedMsgType: Int,
    val lastedMsgAttachType: Int,
    val lastedMsgAttachNo: Int,
    val unreadCount: Int,
    val userNos: List<Int>,
    val notification: Boolean,
    val favorite: Boolean,
    val lastedMsgAttachName: String,
    val msgUserNo: Int,
    val roomType: Int,
    val groupType: Int,
    val newRoom: Boolean
)
