package com.crewcloud.apps.crewchat.data.dto.login

import android.os.Build
import com.crewcloud.apps.crewchat.data.dto.base.getTimezoneOffsetInMinutes
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.Locale

/**
 * Created by BM Anderson on 3/7/26.
 */
@Serializable
data class LoginRequest(
    @SerialName("companyDomain")
    val companyDomain: String,

    @SerialName("languageCode")
    val languageCode: String = Locale.getDefault().language.uppercase(Locale.getDefault()),

    @SerialName("timeZoneOffset")
    val timeZoneOffset: String = getTimezoneOffsetInMinutes(),

    @SerialName("userID")
    val userID: String,

    @SerialName("password")
    val password: String,

    @SerialName("mobileOSVersion")
    val mobileOSVersion: String = Build.VERSION.RELEASE
)
