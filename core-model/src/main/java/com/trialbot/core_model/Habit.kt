package com.trialbot.core_model

import com.trialbot.core_model.enum.Difficulty
import com.trialbot.core_model.enum.HabitType
import java.time.Instant

data class Habit(
    val name: String,
    val category: String,
    val type: HabitType,
    val description: String?,
    val difficulty: Difficulty,
    val lastNegativeActivationDate: Instant?,
    val id: Int?
)