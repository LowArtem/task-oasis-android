package com.trialbot.core_utils

import com.trialbot.core_model.enum.NotificationInterval
import java.time.Instant
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.temporal.ChronoUnit

fun NotificationInterval.toInstant(deadline: Instant, dayNotificationTime: LocalDateTime): Instant? = when (this) {
    NotificationInterval.NONE -> null
    NotificationInterval.IN_TIME -> deadline
    NotificationInterval.FIVE_MINUTES_BEFORE -> deadline.minus(5, ChronoUnit.MINUTES)
    NotificationInterval.ONE_HOUR_BEFORE -> deadline.minus(1, ChronoUnit.HOURS)
    NotificationInterval.IN_DAY -> getInDayInstant(deadline, dayNotificationTime)
    NotificationInterval.IN_DAY_BEFORE -> {
        getInDayInstant(deadline, dayNotificationTime).minus(1, ChronoUnit.DAYS)
    }
    NotificationInterval.IN_WEEK_BEFORE -> {
        getInDayInstant(deadline, dayNotificationTime).minus(1, ChronoUnit.WEEKS)
    }
}

private fun getInDayInstant(deadline: Instant, dayNotificationTime: LocalDateTime): Instant {
    var deadlineTime = deadline.toLocalDateTimeCurrentZone()
    deadlineTime = LocalDateTime.of(
        deadlineTime.year,
        deadlineTime.month,
        deadlineTime.dayOfMonth,
        dayNotificationTime.hour,
        dayNotificationTime.minute,
        0
    )
    return deadlineTime.toInstant(OffsetDateTime.now().offset)
}