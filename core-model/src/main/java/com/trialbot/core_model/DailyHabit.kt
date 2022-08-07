package com.trialbot.core_model

import com.trialbot.core_model.enum.Difficulty
import java.time.Instant

data class DailyHabit(
    val name: String,
    val category: String,
    val deadline: Instant?,
    val status: Boolean,
    val description: String?,
    val difficulty: Difficulty,
    val id: Int?
)
