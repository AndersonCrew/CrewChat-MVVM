package com.crewcloud.apps.crewchat.data.dto.current_chat

import com.crewcloud.apps.crewchat.data.dto.base.getTimezoneOffsetInMinutes
import kotlinx.serialization.Serializable
import java.util.Locale

/**
 * Created by BM Anderson on 12/7/26.
 */
@Serializable
data class CurrentChatRequest(
    val command: String = "GetChatListData",
    val sessionId: String,
    val languageCode: String = Locale.getDefault().language.uppercase(Locale.getDefault()),
    val timeZoneOffset: String = getTimezoneOffsetInMinutes(),
    val reqJson: String = ""
)