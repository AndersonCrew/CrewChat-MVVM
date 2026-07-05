package com.crewcloud.apps.crewchat.data.dto.insert_fcm

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by BM Anderson on 5/7/26.
 */
@JsonClass(generateAdapter = true)
data class NotificationOptions(
    val enabled: Boolean,
    val sound: Boolean,
    val vibrate: Boolean,
    @param:Json(name = "notitime")
    val notificationTime: Boolean,
    @param:Json(name = "starttime")
    val startTime: String,
    @param:Json(name = "endtime")
    val endTime: String,
    @param:Json(name = "confirmonline")
    val confirmOnline: Boolean?= null,
)

@JsonClass(generateAdapter = true)
data class WrapperNotificationOptions(
    @param:Json(name = "DeviceType")
    val deviceType: String = "Android",
    @param:Json(name = "DeviceID")
    val deviceID: String,
    @param:Json(name = "NotifcationOptions")
    val notificationOptions: NotificationOptions
)