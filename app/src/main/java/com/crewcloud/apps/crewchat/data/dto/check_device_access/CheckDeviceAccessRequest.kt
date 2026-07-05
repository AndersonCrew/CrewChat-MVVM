package com.crewcloud.apps.crewchat.data.dto.check_device_access

import com.crewcloud.apps.crewchat.data.dto.base.getTimezoneOffsetInMinutes
import java.util.Locale

/**
 * Created by BM Anderson on 5/7/26.
 */
data class CheckDeviceAccessRequest(
    val sessionId: String,
    val languageCode: String = Locale.getDefault().language.uppercase(Locale.getDefault()),
    val timeZoneOffset: String = getTimezoneOffsetInMinutes(),
    val mobileType: String = "Android",
    val mobileModuleName: String = "CrewChat",
    val mobileDeviceId: String,
    val mobileUUID: String,
    val mobileDeviceName: String = android.os.Build.MODEL,
    val mobileOSVersion: String = android.os.Build.VERSION.RELEASE
)
