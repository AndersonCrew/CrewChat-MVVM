package com.crewcloud.apps.crewchat.data.dto.chatting

import com.crewcloud.apps.crewchat.data.dto.base.getTimezoneOffsetInMinutes
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.Locale

/**
 * Created by BM Anderson on 12/7/26.
 */
@Serializable
data class ChattingRequest(
    val command: String = "GetChatMsgSection_Time",
    val sessionId: String,
    val languageCode: String = Locale.getDefault().language.uppercase(Locale.getDefault()),
    val timeZoneOffset: String = getTimezoneOffsetInMinutes(),
    val reqJson: String
) {
    companion object {
        fun create(
            sessionId: String,
            roomNo: Int,
            getType: Int,
            baseDate: String
        ): ChattingRequest {
            return ChattingRequest(
                sessionId = sessionId,
                reqJson = Json.encodeToString(
                    ChattingRequestJson(
                        roomNo = roomNo,
                        getType = getType,
                        baseDate = baseDate
                    )
                )
            )
        }
    }
}

@Serializable
data class ChattingRequestJson(
    val roomNo: Int,
    val getType: Int,
    val baseDate: String
)

object ChattingGetType {
    const val NONE = 0
    const val FIRST = 1
    const val BEFORE = 2
    const val AFTER = 3
    const val FILTER = 4
}
