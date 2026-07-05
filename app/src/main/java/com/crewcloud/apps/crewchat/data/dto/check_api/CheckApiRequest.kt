package com.crewcloud.apps.crewchat.data.dto.check_api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by BM Anderson on 3/7/26.
 */
@JsonClass(generateAdapter = true)
data class CheckApiRequest(
    @param:Json(name = "Domain")
    val domain: String,

    @param:Json(name = "Applications")
    val applications: String = "CrewChat",

    @param:Json(name = "Mobile_OS")
    val mobileOS: String = "Android",

    @param:Json(name = "ApiName")
    val apiName: String
) {
    companion object ApiNameCheck {
        const val LOGIN_CREWCHAT = "Login_CrewChat"
        const val MOBILE_DEVICES_ACCESS = "CheckMobileDevicesAccessrestrictions"
    }
}
