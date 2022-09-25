package com.trialbot.feature_tasks.data.model

import com.trialbot.core_model.Task
import com.trialbot.core_model.enum.Difficulty
import com.trialbot.core_model.enum.NotificationInterval
import com.trialbot.core_model.enum.Priority
import com.trialbot.core_model.enum.RepeatingInterval
import java.util.*

data class TaskResponse(
    val name: String,
    val deadline: Date?,
    val status: Boolean,
    val difficulty: Int,
    val priority: Int,
    val description: String?,
    val repeatingInterval: Int,
    val notification: Int,
    val completionDate: Date?,
    val id: Int?,
    val subtasks: Set<SubtaskResponse> = setOf()
)

fun TaskResponse.toTask() = Task(
    name = name,
    deadline = deadline?.toInstant(),
    status = status,
    difficulty = Difficulty.values()[difficulty],
    priority = Priority.values()[priority],
    description = description,
    repeatingInterval = RepeatingInterval.values()[repeatingInterval],
    notification = NotificationInterval.values()[notification],
    completionDate = completionDate?.toInstant(),
    id = id,
    subtasks = subtasks.map { it.toSubtask(id ?: -1) }.toSet()
)