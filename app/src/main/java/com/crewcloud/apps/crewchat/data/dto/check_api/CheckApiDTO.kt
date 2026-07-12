package com.crewcloud.apps.crewchat.data.dto.check_api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * Created by BM Anderson on 3/7/26.
 */
@Serializable
data class CheckApiDTO(
    @SerialName("API")
    private val api: Boolean? = false
) {
    val apiValue: Boolean
        get() = api ?: false

}