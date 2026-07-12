package com.crewcloud.apps.crewchat.data.dto.check_ssl

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * Created by BM Anderson on 3/7/26.
 */
@Serializable
data class CheckSSLDTO(
    @SerialName("SSL")
    private val ssl: Boolean? = false
) {
    val sslValue: Boolean
        get() = ssl ?: false

}