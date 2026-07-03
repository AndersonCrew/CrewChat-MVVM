package com.crewcloud.apps.crewchat.data.dto.base

import java.util.TimeZone

/**
 * Created by BM Anderson on 3/7/26.
 */

fun getTimezoneOffsetInMinutes(): String {
    val tz = TimeZone.getDefault()
    var offsetMinutes = tz.rawOffset / 60000
    var sign = ""
    if (offsetMinutes < 0) {
        sign = "-"
        offsetMinutes = -offsetMinutes
    }
    return sign + "" + offsetMinutes
}