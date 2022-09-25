package com.trialbot.feature_tasks.data.model

import com.trialbot.core_model.Task
import java.time.Instant

data class TaskReceive(
    val name: String,
    val deadline: Instant?,
    val difficulty: Int,
    val priority: Int,
    val description: String?,
    val repeatingInterval: Int,
    val notification: Int,
    val subtasks: Set<SubtaskReceive>?
)

data class TaskUpdateReceive(
    val name: String,
    val deadline: Instant?,
    val status: Boolean,
    val difficulty: Int,
    val priority: Int,
    val description: String?,
    val repeatingInterval: Int,
    val notification: Int,
    val id: Int?,
    val subtasks: Set<SubtaskUpdateReceive>
)

data class TaskStatusReceive(
    val taskId: Int,
    val status: Boolean
)

fun Task.toTaskReceive() = TaskReceive(
    name = name,
    deadline = deadline,
    difficulty = difficulty.ordinal,
    priority = priority.ordinal,
    description = description,
    repeatingInterval = repeatingInterval.ordinal,
    notification = notification.ordinal,
    subtasks = subtasks.map { it.toSubtaskReceive() }.toSet()
)

fun Task.toTaskUpdateReceive() = TaskUpdateReceive(
    name = name,
    deadline = deadline,
    difficulty = difficulty.ordinal,
    priority = priority.ordinal,
    description = description,
    repeatingInterval = repeatingInterval.ordinal,
    notification = notification.ordinal,
    subtasks = subtasks.map { it.toSubtaskUpdateReceive() }.toSet(),
    status = status,
    id = id
)

fun Task.toTaskStatusReceive() = TaskStatusReceive(
    taskId = id ?: -1,
    status = status
)