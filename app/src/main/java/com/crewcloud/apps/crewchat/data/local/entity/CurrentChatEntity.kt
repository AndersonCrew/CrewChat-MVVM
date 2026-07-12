package com.crewcloud.apps.crewchat.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Created by BM Anderson on 12/7/26.
 */
@Entity(
    tableName = "current_chats",
    indices = [
        Index(value = ["lastedMsgDate"]),
        Index(value = ["favorite"]),
        Index(value = ["unreadCount"])
    ]
)
data class CurrentChatEntity(
    @PrimaryKey val roomNo: Int,
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
    val userNos: String,
    val notification: Boolean,
    val favorite: Boolean,
    val lastedMsgAttachName: String,
    val msgUserNo: Int,
    val roomType: Int,
    val groupType: Int,
    val newRoom: Boolean
)
