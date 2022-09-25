package com.trialbot.feature_tasks.data.model

import com.trialbot.core_model.Subtask

data class SubtaskResponse(
    val text: String,
    val status: Boolean,
    val id: Int
)

fun SubtaskResponse.toSubtask(parentTaskId: Int) = Subtask(
    text = text,
    status = status,
    parentTaskId = parentTaskId,
    id = id
)