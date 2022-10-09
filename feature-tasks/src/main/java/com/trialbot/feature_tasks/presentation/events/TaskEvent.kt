package com.trialbot.feature_tasks.presentation.events

import com.trialbot.core_model.Subtask
import com.trialbot.core_model.enum.Difficulty
import com.trialbot.core_model.enum.NotificationInterval
import com.trialbot.core_model.enum.Priority
import com.trialbot.core_model.enum.RepeatingInterval
import com.trialbot.feature_tasks.data.model.SubtaskReceive
import com.trialbot.feature_tasks.data.model.SubtaskUpdateReceive
import java.time.LocalDate
import java.time.LocalTime

sealed class TaskEvent {
    data class ChangedTaskStatus(val taskId: Int, val status: Boolean) : TaskEvent()
    data class OpenedTask(val taskId: Int) : TaskEvent()
    data class DeletedTask(val taskId: Int) : TaskEvent()
    object RefreshedTasks : TaskEvent()
    data class OccurredError(val error: Throwable) : TaskEvent()
}

sealed class TaskAddEditEvent {
    data class EnteredTitle(val taskId: Int, val title: String) : TaskAddEditEvent()
    data class EnteredDescription(val taskId: Int, val title: String) : TaskAddEditEvent()
    data class SubtaskAdded(val newSubtask: SubtaskReceive) : TaskAddEditEvent()
    data class SubtaskEdited(val editedSubtask: SubtaskUpdateReceive) : TaskAddEditEvent()
    data class SubtaskDeleted(val editedSubtask: Subtask) : TaskAddEditEvent()
    data class DeadlineDateChanged(val newDate: LocalDate) : TaskAddEditEvent()
    data class DeadlineTimeChanged(val newTime: LocalTime) : TaskAddEditEvent()
    data class NotificationTypeChanged(val newNotificationInterval: NotificationInterval) : TaskAddEditEvent()
    data class RepeatTypeChanged(val newRepeatingInterval: RepeatingInterval) : TaskAddEditEvent()
    data class DifficultyChanged(val newDifficulty: Difficulty) : TaskAddEditEvent()
    data class PriorityChanged(val newPriority: Priority) : TaskAddEditEvent()
    object OnBackPressed : TaskAddEditEvent()
    data class OccurredError(val error: Throwable) : TaskAddEditEvent()
}

sealed class UiEvent {
    data class ShowShackbar(val message: String) : UiEvent()
    data class NavigateToTaskEdit(val taskId: Int) : UiEvent()
}

sealed class TaskAddEditUiEvent {
    data class ShowShackbar(val message: String) : TaskAddEditUiEvent()
    object NavigateBack : TaskAddEditUiEvent()
}