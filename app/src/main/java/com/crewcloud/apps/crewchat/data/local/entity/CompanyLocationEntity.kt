package com.crewcloud.apps.crewchat.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

/**
 * Created by BM Anderson on 3/7/26.
 */
@JsonClass(generateAdapter = true)
@Entity(tableName = "company-location")
data class CompanyLocationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val description: String,
    val locationNo: Int,
    val latitude: Double,
    val longitude: Double,
    val isWorking: Boolean,
    val errorRange: Int
)