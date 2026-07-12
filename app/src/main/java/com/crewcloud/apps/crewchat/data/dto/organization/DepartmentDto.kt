package com.crewcloud.apps.crewchat.data.dto.organization

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by BM Anderson on 6/7/26.
 */
@Serializable
data class DepartmentDto(
    @SerialName("DepartNo")
    val departNo: Int?= null,

    @SerialName("ModUserNo")
    val modUserNo: Int?= null,

    @SerialName("ModDate")
    val modDate: String?= null,

    @SerialName("ParentNo")
    val parentNo: Int?= null,

    @SerialName("Name_Default")
    val nameDefault: String?= null,

    @SerialName("Name_EN")
    val nameEN: String?= null,

    @SerialName("Name_CH")
    val nameCH: String?= null,

    @SerialName("Name_JP")
    val nameJP: String?= null,

    @SerialName("Name_VN")
    val nameVN: String?= null,

    @SerialName("Name")
    val name: String?= null,

    @SerialName("ShortName")
    val shortName: String?= null,

    @SerialName("SortNo")
    val sortNo: Int?= null,

    @SerialName("Enabled")
    val enable: Boolean?= null,

    @SerialName("SenderName")
    val senderName: String?= null,

    @SerialName("ChildDepartments")
    val childDepartments: List<DepartmentDto>,
)
