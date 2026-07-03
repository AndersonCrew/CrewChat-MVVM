package com.crewcloud.apps.crewchat.data.mapper

import com.crewcloud.apps.crewchat.data.dto.login.CompanyLocationDTO
import com.crewcloud.apps.crewchat.data.dto.login.UserDTO
import com.crewcloud.apps.crewchat.data.local.entity.CompanyLocationEntity
import com.crewcloud.apps.crewchat.data.local.entity.UserEntity
import com.crewcloud.apps.crewchat.domain.model.CompanyLocation
import com.crewcloud.apps.crewchat.domain.model.User

/**
 * Created by BM Anderson on 3/7/26.
 */

fun UserDTO.toDomain(): User {
    return User(
        userId = this.userId.orEmpty(),
        fullName = this.fullName.orEmpty(),
        id = this.id ?: 0,
        avatarUrl = this.avatar.orEmpty(),
        permissionType = this.permissionType ?: 0,
        companyName = this.nameCompany.orEmpty(),
        mailAddress = this.mailAddress.orEmpty(),
        companyLocations = this.informationCompany?.map { it.toDomain() }.orEmpty(),
        crewDdsServerIp = this.crewDdsServerIp.orEmpty(),
        crewChatFileServerIp = this.crewChatFileServerIp.orEmpty(),
        crewDdsServerPort = this.crewDdsServerPort ?: 0,
        crewChatFileServerPort = this.crewChatFileServerPort ?: 0,
        companyNo = this.companyNo ?: 0,
        session = this.session ?: "",
    )
}

fun CompanyLocationDTO.toDomain(): CompanyLocation {
    return CompanyLocation(
        description = this.description.orEmpty(),
        locationNo = this.locationNo ?: 0,
        latitude = this.latitude ?: 0.0,
        longitude = this.longitude ?: 0.0,
        isWorking = this.isWorking == 0,
        errorRange = this.errorRange ?: 0,
    )
}

fun CompanyLocationEntity.toDomain(): CompanyLocation {
    return CompanyLocation(
        description = this.description,
        locationNo = this.locationNo,
        latitude = this.latitude,
        longitude = this.longitude,
        isWorking = this.isWorking,
        errorRange = this.errorRange,
    )
}

fun CompanyLocation.toEntity(): CompanyLocationEntity {
    return CompanyLocationEntity(
        description = this.description,
        locationNo = this.locationNo,
        latitude = this.latitude,
        longitude = this.longitude,
        isWorking = this.isWorking,
        errorRange = this.errorRange
    )
}

fun User.toEntity(): UserEntity {
    return UserEntity(
        userId = this.userId,
        fullName = this.fullName,
        id = this.id,
        avatarUrl = this.avatarUrl,
        permissionType = this.permissionType,
        companyName = this.companyName,
        mailAddress = this.mailAddress,
        companyLocations = this.companyLocations.map { it.toEntity() },
        companyNo = this.companyNo
    )
}



fun UserEntity.toDomain(): User {
    return User(
        userId = this.userId,
        fullName = this.fullName,
        id = this.id,
        avatarUrl = this.avatarUrl,
        permissionType = this.permissionType,
        companyName = this.companyName,
        mailAddress = this.mailAddress,
        companyLocations = this.companyLocations.map { it.toDomain() },
        crewDdsServerIp = "",
        crewChatFileServerIp = "",
        crewDdsServerPort = 0,
        crewChatFileServerPort = 0,
        companyNo = this.companyNo,
        session = "",
    )
}

