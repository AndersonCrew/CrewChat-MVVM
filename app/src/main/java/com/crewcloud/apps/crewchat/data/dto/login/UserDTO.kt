package com.crewcloud.apps.crewchat.data.dto.login
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by BM Anderson on 3/7/26.
 */

@JsonClass(generateAdapter = true)
data class UserDTO(
    @param:Json(name = "userID")
    val userId: String? = "",

    @param:Json(name = "FullName")
    val fullName: String? = "",

    @param:Json(name = "Id")
    val id: Int? = 0,

    @param:Json(name = "session")
    val session: String? = "",

    @param:Json(name = "avatar")
    val avatar: String? = "",

    @param:Json(name = "PermissionType")
    val permissionType: Int? = 0,

    @param:Json(name = "NameCompany")
    val nameCompany: String? = "",

    @param:Json(name = "MailAddress")
    val mailAddress: String? = "",

    @param:Json(name = "informationcompany")
    val informationCompany: List<CompanyLocationDTO>? = emptyList(),

    @param:Json(name = "CrewDDSServerIP")
    val crewDdsServerIp: String? = "",

    @param:Json(name = "CrewDDSServerPort")
    val crewDdsServerPort: Int? = 0,

    @param:Json(name = "CrewChatFileServerIP")
    val crewChatFileServerIp: String? = "",

    @param:Json(name = "CrewChatUseReadCount")
    val crewChatUseReadCount: Boolean? = false,

    @param:Json(name = "CrewChatFileServerPort")
    val crewChatFileServerPort: Int? = 0,

    @param:Json(name = "CrewChatLocalDatabase")
    val crewChatLocalDatabase: Boolean? = false,

    @param:Json(name = "CompanyNo")
    val companyNo: Int? = 0
)