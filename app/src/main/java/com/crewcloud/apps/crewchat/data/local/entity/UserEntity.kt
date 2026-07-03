package com.crewcloud.apps.crewchat.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.crewcloud.apps.crewchat.domain.model.CompanyLocation
import com.squareup.moshi.JsonClass

/**
 * Created by BM Anderson on 2/7/26.
 */
@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey
    val userId: String,
    val fullName: String,
    val id: Int,
    val avatarUrl: String,
    val permissionType: Int,
    val companyName: String,
    val mailAddress: String,
    val companyLocations: List<CompanyLocationEntity>,
    val companyNo: Int
)


