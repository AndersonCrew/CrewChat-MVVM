package com.crewcloud.apps.crewchat.data.mapper

import com.crewcloud.apps.crewchat.data.dto.organization.DepartmentDto
import com.crewcloud.apps.crewchat.data.local.entity.DepartmentEntity
import com.crewcloud.apps.crewchat.domain.model.Department
import com.crewcloud.apps.crewchat.domain.model.Employee

/**
 * Created by BM Anderson on 6/7/26.
 */

fun DepartmentDto.toDomain(): Department {
    return Department(
        departNo = this.departNo ?: 0,
        modUserNo = this.modUserNo ?: 0,
        modDate = this.modDate ?: "",
        parentNo = this.parentNo ?: 0,
        nameDefault = this.nameDefault ?: "",
        nameEN = this.nameEN ?: "",
        nameCH = this.nameCH ?: "",
        nameJP = this.nameJP ?: "",
        nameVN = this.nameVN ?: "",
        name = this.name ?: "",
        shortName = this.shortName ?: "",
        sortNo = this.sortNo ?: 0,
        enable = this.enable ?: true,
        senderName = this.senderName ?: "",
        users = emptyList(),
        childDepartments = this.childDepartments.map { it.toDomain() }
    )
}

fun Department.toEntity(): DepartmentEntity {
    return DepartmentEntity(
        departNo = departNo,
        modUserNo = modUserNo,
        modDate = modDate,
        parentNo = parentNo,
        nameDefault = nameDefault,
        nameEN = nameEN,
        nameCH = nameCH,
        nameJP = nameJP,
        nameVN = nameVN,
        name = name,
        shortName = shortName,
        sortNo = sortNo,
        enable = enable,
        senderName = senderName,
        isExpanded = isExpanded
    )
}

fun DepartmentEntity.toDomain(
    childDepartments: List<Department> = emptyList(),
): Department {
    return Department(
        departNo = departNo,
        modUserNo = modUserNo,
        modDate = modDate,
        parentNo = parentNo,
        nameDefault = nameDefault,
        nameEN = nameEN,
        nameCH = nameCH,
        nameJP = nameJP,
        nameVN = nameVN,
        name = name,
        shortName = shortName,
        sortNo = sortNo,
        enable = enable,
        senderName = senderName,
        childDepartments = childDepartments,
        users = emptyList(),
        isExpanded = isExpanded
    )
}

fun Department.flattenToEntities(): List<DepartmentEntity> {
    return listOf(toEntity()) + childDepartments.flatMap { it.flattenToEntities() }
}

fun List<Department>.flattenToDepartmentEntities(): List<DepartmentEntity> {
    return flatMap { it.flattenToEntities() }
}
