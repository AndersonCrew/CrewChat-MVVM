package com.crewcloud.apps.crewchat.presentation.ui.home.chatting

import com.crewcloud.apps.crewchat.domain.model.Chatting
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Created by BM Anderson on 12/7/26.
 */
sealed interface ChatRow {
    val key: String

    data class DateHeader(
        val text: String
    ) : ChatRow {
        override val key: String = "date_$text"
    }

    data class Message(
        val message: Chatting,
        val isMine: Boolean
    ) : ChatRow {
        override val key: String = "message_${message.messageNo}_${message.strRegDate}"
    }
}

fun Chatting.senderName(): String {
    return if (userNo > 0) "User $userNo" else ""
}

fun Chatting.displayTime(): String {
    val date = messageDate() ?: return ""
    return SimpleDateFormat("HH:mm", Locale.getDefault()).format(date)
}

fun Chatting.isMine(currentUserNo: Int): Boolean {
    if (currentUserNo <= 0) return false
    return when {
        writerUser > 0 -> writerUser == currentUserNo
        writerUserNo > 0 -> writerUserNo == currentUserNo
        else -> userNo == currentUserNo
    }
}

fun Chatting.displayDateHeader(): String {
    val date = messageDate() ?: return ""
    return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date)
}

fun Chatting.dateKey(): String {
    val date = messageDate() ?: return ""
    return SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(date)
}

private fun Chatting.messageDate(): Date? {
    parseServerDate(strRegDate)?.let { return it }
    parseDotNetDate(regDate)?.let { return it }
    return null
}

private fun parseServerDate(value: String): Date? {
    if (value.isBlank()) return null
    val patterns = listOf(
        "yyyy-MM-dd HH:mm:ss.SSS",
        "yyyy-MM-dd HH:mm:ss",
        "yyyy-MM-dd'T'HH:mm:ss.SSS"
    )
    return patterns.firstNotNullOfOrNull { pattern ->
        runCatching {
            SimpleDateFormat(pattern, Locale.getDefault()).parse(value)
        }.getOrNull()
    }
}

private fun parseDotNetDate(value: String): Date? {
    val millis = value
        .substringAfter("Date(", "")
        .takeWhile { it.isDigit() }
        .toLongOrNull() ?: return null
    return Date(millis)
}
