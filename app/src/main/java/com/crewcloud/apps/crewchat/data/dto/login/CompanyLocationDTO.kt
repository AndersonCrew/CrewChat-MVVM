package com.crewcloud.apps.crewchat.data.dto.login

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by BM Anderson on 3/7/26.
 */
@Serializable
data class CompanyLocationDTO(
    @SerialName("Description")
    val description: String? = "",

    @SerialName("LocationNo")
    val locationNo: Int? = 0,

    @SerialName("Latitude")
    val latitude: Double? = 0.0,

    @SerialName("Longitude")
    val longitude: Double? = 0.0,

    @SerialName("IsWorking")
    val isWorking: Int? = 0,

    @SerialName("ErrorRange")
    val errorRange: Int? = 0
)
