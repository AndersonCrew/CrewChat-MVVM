package com.crewcloud.apps.crewchat.data.dto.insert_fcm

import com.crewcloud.apps.crewchat.data.dto.base.Data

/**
 * Created by BM Anderson on 5/7/26.
 */
data class UpdateNotificationResponse(
    val d: DataInsert
)

data class DataInsert(
    val success: Boolean
)