package com.crewcloud.apps.crewchat.data.mapper

import com.crewcloud.apps.crewchat.data.dto.check_api.CheckApiDTO
import com.crewcloud.apps.crewchat.domain.model.CheckApi

/**
 * Created by BM Anderson on 3/7/26.
 */

fun CheckApiDTO.toDomain(): CheckApi {
    return CheckApi(
        api = this.apiValue
    )
}