package com.trialbot.core_model

import com.trialbot.core_model.enum.Difficulty
import com.trialbot.core_model.enum.NotificationInterval
import com.trialbot.core_model.enum.Priority
import com.trialbot.core_model.enum.RepeatingInterval
import java.time.Instant

data class Task(
    val name: String,
    val deadline: Instant?,
    val status: Boolean,
    val difficulty: Difficulty,
    val priority: Priority,
    val description: String?,
    val repeatingInterval: RepeatingInterval,
    val notification: NotificationInterval,
    val completionDate: Instant?,
    val id: Int?,
    val subtasks: Set<Subtask>
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
