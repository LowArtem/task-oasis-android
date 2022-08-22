package com.trialbot.feature_tasks.presentation.events

sealed class TaskEvent {
    data class ChangedTaskStatus(val taskId: Int, val status: Boolean) : TaskEvent()
    data class OpenedTask(val taskId: Int) : TaskEvent()
    data class DeletedTask(val taskId: Int) : TaskEvent()
    object RefreshedTasks : TaskEvent()
    data class OccurredError(val error: Throwable) : TaskEvent()
}

sealed class UiEvent {
    data class ShowShackbar(val message: String) : UiEvent()
    data class NavigateToTaskEdit(val taskId: Int) : UiEvent()
}