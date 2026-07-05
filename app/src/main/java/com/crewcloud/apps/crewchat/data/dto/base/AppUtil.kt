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

fun timeToStringNotAMPM(hourOfDay: Int, minute: Int): String {
    var text = ""
    var minutes = ""
    if (minute < 10) {
        minutes = "0$minute"
    } else {
        minutes = minute.toString()
    }
    if ((hourOfDay == 12 && minute > 0) || hourOfDay > 12) { // PM
        text += "$hourOfDay:$minutes"
    } else { // AM
        if (hourOfDay < 10) {
            text += "0"
        }
        text += "$hourOfDay:$minutes"
    }
    return text
}