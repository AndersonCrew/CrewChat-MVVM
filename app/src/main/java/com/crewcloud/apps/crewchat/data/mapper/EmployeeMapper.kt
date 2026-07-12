package com.crewcloud.apps.crewchat.data.mapper

import com.crewcloud.apps.crewchat.data.dto.organization.EmployeeDto
import com.crewcloud.apps.crewchat.data.local.entity.EmployeeEntity
import com.crewcloud.apps.crewchat.domain.model.Employee

/**
 * Created by BM Anderson on 6/7/26.
 */

fun EmployeeDto.toDomain(): Employee {
    return Employee(
        id = this.userNo?: -1,
        username = this.userId,
        displayName = this.nameVn?.ifEmpty { this.name } ?: this.name,
        email = this.mailAddress ?: "",
        phoneNumber = this.cellPhone ?: "",
        companyNumber = this.companyPhone ?: "",
        extNumber = this.extensionNumber ?: "",
        gender = this.sex,
        isUserPhotoActive = this.userPhoto,
        photoName = this.photo ?: "",
        isActive = this.enabled,
        departmentId = this.departNo,
        departmentName = this.departName ?: "",
        position = this.positionName ?: "",
        duty = this.dutyName ?: "",
        fullComboName = this.nameAndUserId ?: this.name,
        avatar = this.avatarUrl ?: ""
    )
}

fun EmployeeDto.toEntity(url: String? = ""): EmployeeEntity {
    return EmployeeEntity(
        userNo = this.userNo?: -1,
        modUserNo = this.modUserNo,
        modDate = this.modDate ?: "",
        userId = this.userId,
        passwordChangeDate = this.passwordChangeDate ?: "",
        nameDefault = this.nameDefault,
        nameEn = this.nameEn ?: "",
        nameCh = this.nameCh ?: "",
        nameJp = this.nameJp ?: "",
        nameVn = this.nameVn ?: "",
        name = this.name,
        mailAddress = this.mailAddress ?: "",
        sex = this.sex,
        cellPhone = this.cellPhone ?: "",
        companyPhone = this.companyPhone ?: "",
        extensionNumber = this.extensionNumber ?: "",
        entranceDateBool = this.entranceDateBool,
        birthDateBool = this.birthDateBool,
        birthDateType = this.birthDateType,
        userPhoto = this.userPhoto,
        photo = this.photo ?: "",
        isEnabled = this.enabled,
        isVirtual = this.isVirtual,
        departNo = this.departNo,
        departName = this.departName ?: "",
        departSortNo = this.departSortNo,
        positionNo = this.positionNo,
        positionName = this.positionName ?: "",
        positionSortNo = this.positionSortNo,
        dutyNo = this.dutyNo,
        dutyName = this.dutyName ?: "",
        dutySortNo = this.dutySortNo,
        nameAndUserId = this.nameAndUserId ?: "",
        avatarUrl = url + this.avatarUrl
    )
}

fun EmployeeEntity.toDomain(): Employee {
    return Employee(
        id = this.userNo,
        username = this.userId,
        displayName = this.nameVn.ifEmpty { this.name },
        email = this.mailAddress,
        phoneNumber = this.cellPhone,
        companyNumber = this.companyPhone,
        extNumber = this.extensionNumber,
        gender = this.sex,
        isUserPhotoActive = this.userPhoto,
        photoName = this.photo,
        isActive = this.isEnabled,
        departmentId = this.departNo,
        departmentName = this.departName,
        position = this.positionName,
        duty = this.dutyName,
        fullComboName = this.nameAndUserId,
        avatar = this.avatarUrl
    )
}