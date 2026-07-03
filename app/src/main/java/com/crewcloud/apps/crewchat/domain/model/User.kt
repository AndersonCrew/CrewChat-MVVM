package com.crewcloud.apps.crewchat.domain.model

/**
 * Created by BM Anderson on 3/7/26.
 */
data class User(
    val userId: String,
    val fullName: String,
    val id: Int,
    val session: String,
    val avatarUrl: String,
    val permissionType: Int,
    val companyName: String,
    val mailAddress: String,
    val companyLocations: List<CompanyLocation>,
    val crewDdsServerIp: String,
    val crewDdsServerPort: Int,
    val crewChatFileServerIp: String,
    val crewChatFileServerPort: Int,
    val companyNo: Int
)
