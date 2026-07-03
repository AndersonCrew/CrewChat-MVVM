package com.crewcloud.apps.crewchat.data.mapper

import com.crewcloud.apps.crewchat.data.dto.check_ssl.CheckSSLDTO
import com.crewcloud.apps.crewchat.domain.model.CheckSSL

/**
 * Created by BM Anderson on 3/7/26.
 */

fun CheckSSLDTO.toDomain(): CheckSSL {
    return CheckSSL(
        ssl = this.sslValue
    )
}