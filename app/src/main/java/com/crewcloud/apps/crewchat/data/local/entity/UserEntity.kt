package com.crewcloud.apps.crewchat.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

/**
 * Created by BM Anderson on 2/7/26.
 */
@Entity(tableName = "User")
data class UserEntity(
    @PrimaryKey
    val id: Int
)
