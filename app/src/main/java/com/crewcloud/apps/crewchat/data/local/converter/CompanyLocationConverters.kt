package com.crewcloud.apps.crewchat.data.local.converter

import androidx.room.TypeConverter
import com.crewcloud.apps.crewchat.data.local.entity.CompanyLocationEntity
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

/**
 * Created by BM Anderson on 3/7/26.
 */
class CompanyLocationConverters {
    private val moshi = Moshi.Builder().build()
    private val type = Types.newParameterizedType(List::class.java, CompanyLocationEntity::class.java)
    private val adapter = moshi.adapter<List<CompanyLocationEntity>>(type)

    @TypeConverter
    fun fromString(value: String?): List<CompanyLocationEntity>? {
        return value?.let { adapter.fromJson(it) }
    }

    @TypeConverter
    fun fromList(list: List<CompanyLocationEntity>?): String? {
        return adapter.toJson(list)
    }
}