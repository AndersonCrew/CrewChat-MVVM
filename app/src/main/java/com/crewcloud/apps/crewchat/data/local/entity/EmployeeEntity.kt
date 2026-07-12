package com.crewcloud.apps.crewchat.data.local.entity

/**
 * Created by BM Anderson on 6/7/26.
 */
import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "employees",
    primaryKeys = ["userNo", "departNo"],
    indices = [
        Index(value = ["departNo"]),
        Index(value = ["userNo"])
    ]
)

data class EmployeeEntity(
    val userNo: Int,
    val modUserNo: Int,
    val modDate: String,
    val userId: String,
    val passwordChangeDate: String,
    val nameDefault: String,
    val nameEn: String,
    val nameCh: String,
    val nameJp: String,
    val nameVn: String,
    val name: String,
    val mailAddress: String,
    val sex: Int,
    val cellPhone: String,
    val companyPhone: String,
    val extensionNumber: String,
    val entranceDateBool: Boolean,
    val birthDateBool: Boolean,
    val birthDateType: Int,
    val userPhoto: Boolean,
    val photo: String,
    val isEnabled: Boolean,
    val isVirtual: Boolean,
    val departNo: Int,
    val departName: String,
    val departSortNo: Int,
    val positionNo: Int,
    val positionName: String,
    val positionSortNo: Int,
    val dutyNo: Int,
    val dutyName: String,
    val dutySortNo: Int,
    val nameAndUserId: String,
    val avatarUrl: String
)