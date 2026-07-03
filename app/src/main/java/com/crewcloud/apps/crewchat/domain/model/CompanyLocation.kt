package com.crewcloud.apps.crewchat.domain.model

/**
 * Created by BM Anderson on 3/7/26.
 */
data class CompanyLocation(
    val description: String,
    val locationNo: Int,
    val latitude: Double,
    val longitude: Double,
    val isWorking: Boolean,
    val errorRange: Int
)
