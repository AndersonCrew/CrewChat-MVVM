package com.crewcloud.apps.crewchat.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Created by BM Anderson on 7/7/26.
 */
@Entity(
    tableName = "departments",
    indices = [
        Index(value = ["parentNo"]),
        Index(value = ["sortNo"])
    ]
)
data class DepartmentEntity(
    @PrimaryKey val departNo: Int,
    val modUserNo: Int,
    val modDate: String,
    val parentNo: Int,
    val nameDefault: String,
    val nameEN: String,
    val nameCH: String,
    val nameJP: String,
    val nameVN: String,
    val name: String,
    val shortName: String,
    val sortNo: Int,
    val enable: Boolean,
    val senderName: String,
    val isExpanded: Boolean = false
)
