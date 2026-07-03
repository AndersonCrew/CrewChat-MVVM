package com.crewcloud.apps.crewchat.data.dto.check_ssl

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by BM Anderson on 3/7/26.
 */
@JsonClass(generateAdapter = true)
data class CheckSSLDTO(
    @param:Json(name = "SSL")
    val ssl: Boolean? = false
) {
    val sslValue: Boolean
        get() = ssl ?: false

}