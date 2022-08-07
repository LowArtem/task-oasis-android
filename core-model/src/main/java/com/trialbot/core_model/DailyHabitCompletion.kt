package com.trialbot.core_model

import java.time.Instant

data class DailyHabitCompletion(
    val date: Instant,
    val dailyHabitId: Int,
    val rating: Int,
    val id: Int?
)
