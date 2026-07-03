package com.crewcloud.apps.crewchat.data.dto.check_api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by BM Anderson on 3/7/26.
 */
@JsonClass(generateAdapter = true)
data class CheckApiDTO(
    @param:Json(name = "API")
    val api: Boolean? = false
) {
    val apiValue: Boolean
        get() = api ?: false

}