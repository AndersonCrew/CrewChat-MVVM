package com.crewcloud.apps.crewchat.domain.model

/**
 * Created by BM Anderson on 12/7/26.
 */
data class Chatting(
    val roomNo: Long,
    val makeUserNo: Int,
    val modDate: String,
    val isOne: Boolean,
    val roomTitle: String,
    val lastedMsg: String,
    val lastedMsgAttachType: Int,
    val lastedMsgType: Int,
    val lastedMsgDate: String,
    val userNos: List<Int>,
    val writerUser: Int,
    val writerUserNo: Int,
    val messageNo: Long,
    val userNo: Int,
    val msgUserNo: Int,
    val message: String,
    val type: Int,
    val attachNo: Int,
    val regDate: String,
    val strRegDate: String,
    val unreadCount: Int,
    val attachFileName: String,
    val attachFileType: Int,
    val attachFilePath: String,
    val attachFileSize: Int,
    val unreadTotalCount: Int,
    val roomType: Int,
    val notification: Boolean,
    val favorite: Boolean,
    val hasSent: Boolean = true,
    val isHeader: Boolean = false,
    val isSendTemp: Boolean = false
)
