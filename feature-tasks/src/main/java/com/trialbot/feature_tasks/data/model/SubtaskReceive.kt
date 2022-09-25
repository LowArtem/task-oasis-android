package com.trialbot.feature_tasks.data.model

import com.trialbot.core_model.Subtask

data class SubtaskReceive(
    val text: String,
    val status: Boolean,
)

data class SubtaskUpdateReceive(
    val text: String,
    val status: Boolean,
    val id: Int?
)

fun Subtask.toSubtaskReceive() = SubtaskReceive(
    text = text,
    status = status
)

fun Subtask.toSubtaskUpdateReceive() = SubtaskUpdateReceive(
    text = text,
    status = status,
    id = id
)