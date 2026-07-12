package com.crewcloud.apps.crewchat.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Created by BM Anderson on 12/7/26.
 */
@Entity(
    tableName = "chattings",
    indices = [
        Index(value = ["roomNo", "messageNo"]),
        Index(value = ["roomNo", "strRegDate"]),
        Index(value = ["roomNo", "hasSent"])
    ]
)
data class ChattingEntity(
    @PrimaryKey val messageNo: Long,
    val roomNo: Long,
    val makeUserNo: Int,
    val modDate: String,
    val isOne: Boolean,
    val roomTitle: String,
    val lastedMsg: String,
    val lastedMsgAttachType: Int,
    val lastedMsgType: Int,
    val lastedMsgDate: String,
    val userNos: String,
    val writerUser: Int,
    val writerUserNo: Int,
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
    val hasSent: Boolean,
    val isHeader: Boolean,
    val isSendTemp: Boolean
)
