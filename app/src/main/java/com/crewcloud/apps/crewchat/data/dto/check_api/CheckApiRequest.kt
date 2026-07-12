package com.crewcloud.apps.crewchat.data.dto.check_api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * Created by BM Anderson on 3/7/26.
 */
@Serializable
data class CheckApiRequest(
    @SerialName("Domain")
    val domain: String,

    @SerialName("Applications")
    val applications: String = "CrewChat",

    @SerialName("Mobile_OS")
    val mobileOS: String = "Android",

    @SerialName("ApiName")
    val apiName: String
) {
    companion object ApiNameCheck {
        const val LOGIN_CREWCHAT = "Login_CrewChat"
        const val MOBILE_DEVICES_ACCESS = "CheckMobileDevicesAccessrestrictions"
    }
}
