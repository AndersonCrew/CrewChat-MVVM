package com.crewcloud.apps.crewchat.data.dto.insert_fcm

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by BM Anderson on 5/7/26.
 */
@Serializable
data class UpdateNotificationResponse(
    @SerialName("d") val d: DataInsert
)

@Serializable
data class DataInsert(
    @SerialName("success") val success: Boolean
)