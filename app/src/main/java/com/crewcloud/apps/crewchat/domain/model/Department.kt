package com.crewcloud.apps.crewchat.domain.model

/**
 * Created by BM Anderson on 6/7/26.
 */
data class Department(
    val departNo: Int,
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
    val childDepartments: List<Department>,
    val users: List<Employee>,
    val isExpanded: Boolean = false
)
