package com.crewcloud.apps.crewchat.data.dto.organization

import com.crewcloud.apps.crewchat.data.dto.base.getTimezoneOffsetInMinutes
import kotlinx.serialization.Serializable
import java.util.Locale

/**
 * Created by BM Anderson on 6/7/26.
 */
@Serializable
data class EmployeeRequest(
    val departNo: Int,
    val sessionId: String,
    val languageCode: String = Locale.getDefault().language.uppercase(Locale.getDefault()),
    val timeZoneOffset: String = getTimezoneOffsetInMinutes(),
)
