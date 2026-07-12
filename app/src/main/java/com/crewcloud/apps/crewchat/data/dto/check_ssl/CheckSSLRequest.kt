package com.crewcloud.apps.crewchat.data.dto.check_ssl

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * Created by BM Anderson on 3/7/26.
 */
@Serializable
data class CheckSSLRequest(
    @SerialName("Domain")
    val domain: String,

    @SerialName("Applications")
    val applications: String = "CrewChat",
)
