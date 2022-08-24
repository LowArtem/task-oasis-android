package com.trialbot.core_utils

import java.time.Instant
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.util.*

fun Instant.toLocalDateTime(timeZone: ZoneId): LocalDateTime =
    LocalDateTime.ofInstant(this, timeZone)

fun Instant.toLocalDateTimeUTC(): LocalDateTime = this.toLocalDateTime(ZoneId.of("UTC"))

fun Instant.toLocalDateTimeCurrentZone(): LocalDateTime =
    this.toLocalDateTime(TimeZone.getDefault().toZoneId())

fun LocalDateTime.toStringShortFormatted(): String {

    var day: String = this.dayOfMonth.toString()
    var month: String = this.monthValue.toString()

    if (this.dayOfMonth < 10) {
        day = "0$day"
    }
    if (this.monthValue < 10) {
        month = "0$month"
    }

    return "$day.$month"
}

fun LocalDateTime.toStringFullFormatted(): String {

    var day: String = this.dayOfMonth.toString()
    var month: String = this.monthValue.toString()
    val year: String = this.year.toString()

    if (this.dayOfMonth < 10) {
        day = "0$day"
    }
    if (this.monthValue < 10) {
        month = "0$month"
    }

    return "$day.$month.$year"
}

fun LocalTime.toStringFormatted(): String {
    var hours: String = this.hour.toString()
    var minutes: String = this.minute.toString()

    if (this.hour < 10) {
        hours = "0$hours"
    }
    if (this.minute < 10) {
        minutes = "0$minutes"
    }

    return "$hours:$minutes"
}