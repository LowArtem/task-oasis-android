package com.trialbot.core_model

data class Subtask(
    val text: String,
    val status: Boolean,
    val parentTaskId: Int,
    val id: Int?
)
