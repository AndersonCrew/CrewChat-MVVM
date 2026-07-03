package com.crewcloud.apps.crewchat.data.dto.login

import android.os.Build
import com.crewcloud.apps.crewchat.data.dto.base.getTimezoneOffsetInMinutes
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Locale

/**
 * Created by BM Anderson on 3/7/26.
 */
@JsonClass(generateAdapter = true)
data class LoginRequest(
    @param:Json(name = "companyDomain")
    val companyDomain: String,

    @param:Json(name = "languageCode")
    val languageCode: String = Locale.getDefault().language.uppercase(Locale.getDefault()),

    @param:Json(name = "timeZoneOffset")
    val timeZoneOffset: String = getTimezoneOffsetInMinutes(),

    @param:Json(name = "userID")
    val userID: String,

    @param:Json(name = "password")
    val password: String,

    @param:Json(name = "mobileOSVersion")
    val mobileOSVersion: String = Build.VERSION.RELEASE
)
