package com.crewcloud.apps.crewchat.data.dto.insert_fcm


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by BM Anderson on 5/7/26.
 */
@Serializable
data class NotificationOptions(
    val enabled: Boolean,
    val sound: Boolean,
    val vibrate: Boolean,
    @SerialName("notitime")
    val notificationTime: Boolean,
    @SerialName("starttime")
    val startTime: String,
    @SerialName("endtime")
    val endTime: String,
    @SerialName("confirmonline")
    val confirmOnline: Boolean?= null,
)

@Serializable
data class WrapperNotificationOptions(
    @SerialName("DeviceType")
    val deviceType: String = "Android",
    @SerialName("DeviceID")
    val deviceID: String,
    @SerialName("NotifcationOptions")
    val notificationOptions: NotificationOptions
)