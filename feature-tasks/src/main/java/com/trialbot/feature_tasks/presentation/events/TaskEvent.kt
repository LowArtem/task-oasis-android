package com.trialbot.feature_tasks.presentation.events

sealed class TaskEvent {
    data class ChangedTaskStatus(val taskId: Int, val status: Boolean) : TaskEvent()
    data class OpenedTask(val taskId: Int) : TaskEvent()
    object RefreshedTasks : TaskEvent()
}

sealed class UiEvent {
    data class ShowShackbar(val message: String) : UiEvent()
}