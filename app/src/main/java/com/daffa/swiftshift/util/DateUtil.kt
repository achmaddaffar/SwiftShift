package com.daffa.swiftshift.util

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

object DateUtil {

    fun convertMillisecondToDateFormat(millis: Long): String {
        val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
        val instant = Instant.ofEpochMilli(millis)
        val date = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
        return formatter.format(date)
    }
}