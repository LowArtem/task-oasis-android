package com.trialbot.core_model

import java.time.Instant

data class HabitCompletion(
    val date: Instant,
    val habitId: Int,
    val rating: Int,
    val isPositive: Boolean,
    val id: Int?
)
