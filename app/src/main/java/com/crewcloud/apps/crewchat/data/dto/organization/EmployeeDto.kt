package com.crewcloud.apps.crewchat.data.dto.organization

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by BM Anderson on 6/7/26.
 */

@Serializable
data class EmployeeDto(
    @SerialName("UserNo") val userNo: Int?,
    @SerialName("ModUserNo") val modUserNo: Int,
    @SerialName("ModDate") val modDate: String?,
    @SerialName("UserID") val userId: String,
    @SerialName("Password") val password: String? = null,
    @SerialName("PasswordChangeDate") val passwordChangeDate: String?,
    @SerialName("Name_Default") val nameDefault: String,
    @SerialName("Name_EN") val nameEn: String?,
    @SerialName("Name_CH") val nameCh: String? = "",
    @SerialName("Name_JP") val nameJp: String? = "",
    @SerialName("Name_VN") val nameVn: String? = "",
    @SerialName("Name") val name: String,
    @SerialName("MailAddress") val mailAddress: String?,
    @SerialName("Sex") val sex: Int,
    @SerialName("CellPhone") val cellPhone: String?,
    @SerialName("CompanyPhone") val companyPhone: String?,
    @SerialName("ExtensionNumber") val extensionNumber: String?,
    @SerialName("FaxNumber") val faxNumber: String? = null,
    @SerialName("EntranceDate") val entranceDate: String? = null,
    @SerialName("BirthDate") val birthDate: String? = null,
    @SerialName("EntranceDate_Bool") val entranceDateBool: Boolean,
    @SerialName("BirthDate_Bool") val birthDateBool: Boolean,
    @SerialName("BirthDateType") val birthDateType: Int,
    @SerialName("UserPhoto") val userPhoto: Boolean,
    @SerialName("Photo") val photo: String?,
    @SerialName("Enabled") val enabled: Boolean,
    @SerialName("IsVirtual") val isVirtual: Boolean,
    @SerialName("Belongs") val belongs: String? = null,
    @SerialName("DepartNo") val departNo: Int,
    @SerialName("TopDepartName") val topDepartName: String? = null,
    @SerialName("DepartName") val departName: String?,
    @SerialName("ShortName") val shortName: String? = null,
    @SerialName("DepartSortNo") val departSortNo: Int,
    @SerialName("PositionNo") val positionNo: Int,
    @SerialName("PositionName") val positionName: String?,
    @SerialName("PositionSortNo") val positionSortNo: Int,
    @SerialName("DutyNo") val dutyNo: Int,
    @SerialName("DutyName") val dutyName: String?,
    @SerialName("DutySortNo") val dutySortNo: Int,
    @SerialName("UserAddfield") val userAddField: String? = null,
    @SerialName("NameAndUserID") val nameAndUserId: String?,
    @SerialName("AvatarUrl") val avatarUrl: String?
)