package com.crewcloud.apps.crewchat.data.local.converter

import androidx.room.TypeConverter
import com.crewcloud.apps.crewchat.data.local.entity.CompanyLocationEntity
import kotlinx.serialization.json.Json

/**
 * Created by BM Anderson on 3/7/26.
 */

class CompanyLocationConverters {

    // Khởi tạo một đối tượng Json dùng chung (có thể bật ignoreUnknownKeys nếu cần)
    private val json = Json {
        ignoreUnknownKeys = true
    }

    @TypeConverter
    fun fromString(value: String?): List<CompanyLocationEntity>? {
        if (value.isNullOrEmpty()) return emptyList()
        return try {
            json.decodeFromString<List<CompanyLocationEntity>>(value)
        } catch (e: Exception) {
            emptyList()
        }
    }

    @TypeConverter
    fun fromList(list: List<CompanyLocationEntity>?): String? {
        if (list == null) return null
        return json.encodeToString(list)
    }
}