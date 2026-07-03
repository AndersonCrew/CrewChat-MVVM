package com.crewcloud.apps.crewchat.data.dto.check_ssl

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by BM Anderson on 3/7/26.
 */
@JsonClass(generateAdapter = true)
data class CheckSSLRequest(
    @param:Json(name = "Domain")
    val domain: String,

    @param:Json(name = "Applications")
    val applications: String = "CrewChat",
)
