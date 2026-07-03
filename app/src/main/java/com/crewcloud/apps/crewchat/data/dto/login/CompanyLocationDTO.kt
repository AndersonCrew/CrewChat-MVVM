package com.crewcloud.apps.crewchat.data.dto.login

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by BM Anderson on 3/7/26.
 */
@JsonClass(generateAdapter = true)
data class CompanyLocationDTO(
    @param:Json(name = "Description")
    val description: String? = "",

    @param:Json(name = "LocationNo")
    val locationNo: Int? = 0,

    @param:Json(name = "Latitude")
    val latitude: Double? = 0.0,

    @param:Json(name = "Longitude")
    val longitude: Double? = 0.0,

    @param:Json(name = "IsWorking")
    val isWorking: Int? = 0,

    @param:Json(name = "ErrorRange")
    val errorRange: Int? = 0
)
