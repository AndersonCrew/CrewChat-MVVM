package com.crewcloud.apps.crewchat.data.dto.base

import com.squareup.moshi.JsonClass

/**
 * Created by BM Anderson on 5/7/26.
 */
@JsonClass(generateAdapter = true)
data class EmptyDTO(
    val unused: String? = null
)