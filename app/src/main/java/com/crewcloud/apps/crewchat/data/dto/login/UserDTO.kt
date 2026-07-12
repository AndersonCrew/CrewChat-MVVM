package com.crewcloud.apps.crewchat.data.dto.login

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by BM Anderson on 3/7/26.
 */

@Serializable
data class UserDTO(
    @SerialName("userID")
    val userId: String? = "",

    @SerialName("FullName")
    val fullName: String? = "",

    @SerialName("Id")
    val id: Int? = 0,

    @SerialName("session")
    val session: String? = "",

    @SerialName("avatar")
    val avatar: String? = "",

    @SerialName("PermissionType")
    val permissionType: Int? = 0,

    @SerialName("NameCompany")
    val nameCompany: String? = "",

    @SerialName("MailAddress")
    val mailAddress: String? = "",

    @SerialName("informationcompany")
    val informationCompany: List<CompanyLocationDTO>? = emptyList(),

    @SerialName("CrewDDSServerIP")
    val crewDdsServerIp: String? = "",

    @SerialName("CrewDDSServerPort")
    val crewDdsServerPort: Int? = 0,

    @SerialName("CrewChatFileServerIP")
    val crewChatFileServerIp: String? = "",

    @SerialName("CrewChatUseReadCount")
    val crewChatUseReadCount: Boolean? = false,

    @SerialName("CrewChatFileServerPort")
    val crewChatFileServerPort: Int? = 0,

    @SerialName("CrewChatLocalDatabase")
    val crewChatLocalDatabase: Boolean? = false,

    @SerialName("CompanyNo")
    val companyNo: Int? = 0
)