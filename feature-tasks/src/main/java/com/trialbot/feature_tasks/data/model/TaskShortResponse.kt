package com.trialbot.feature_tasks.data.model

import com.trialbot.core_model.enum.Difficulty
import com.trialbot.core_model.enum.Priority
import java.time.Instant

data class TaskShortResponse(
    val name: String,
    val deadline: Instant,
    val status: Boolean,
    val difficulty: Int,
    val priority: Int,
    val hasNotification: Boolean,
    val hasRepeat: Boolean,
    val id: Int
)

data class TaskShortDto(
    val name: String,
    val deadline: Instant,
    val status: Boolean,
    val difficulty: Difficulty,
    val priority: Priority,
    val hasNotification: Boolean,
    val hasRepeat: Boolean,
    val id: Int
)
