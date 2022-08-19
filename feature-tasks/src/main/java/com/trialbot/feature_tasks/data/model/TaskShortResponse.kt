package com.trialbot.feature_tasks.data.model

import com.trialbot.core_model.enum.Difficulty
import com.trialbot.core_model.enum.Priority
import java.time.Instant
import java.util.Date

data class TaskShortResponse(
    val name: String,
    val deadline: Date?,
    val status: Boolean,
    val difficulty: Int,
    val priority: Int,
    val hasNotification: Boolean,
    val hasRepeat: Boolean,
    val completionDate: Date?,
    val id: Int
)

data class TaskShortDto(
    val name: String,
    val deadline: Instant?,
    val status: Boolean,
    val difficulty: Difficulty,
    val priority: Priority,
    val hasNotification: Boolean,
    val hasRepeat: Boolean,
    val completionDate: Instant?,
    val id: Int
)

fun TaskShortResponse.toDto() = TaskShortDto(
    name = name,
    deadline = deadline?.toInstant(),
    status = status,
    difficulty = Difficulty.values()[difficulty],
    priority = Priority.values()[priority],
    hasNotification = hasNotification,
    hasRepeat = hasRepeat,
    completionDate = completionDate?.toInstant(),
    id = id
)