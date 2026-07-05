package com.crewcloud.apps.crewchat.data.dto.insert_fcm

import com.crewcloud.apps.crewchat.data.dto.base.getTimezoneOffsetInMinutes
import com.squareup.moshi.JsonClass
import java.util.Locale

/**
 * Created by BM Anderson on 5/7/26.
 */

@JsonClass(generateAdapter = true)
data class UpdateNotificationRequest(
    val command: String = "InsertDevice",
    val sessionId: String,
    val languageCode: String = Locale.getDefault().language.uppercase(Locale.getDefault()),
    val timeZoneOffset: String = getTimezoneOffsetInMinutes(),
    val notificationOptions: String,
    val reqJson: String
)