package com.trialbot.feature_tasks.presentation.state

import com.trialbot.core_model.TaskShortDto
import com.trialbot.core_utils.Result

data class ListTabUiState(
    val completedTasks: Result<List<TaskShortDto>>,
    val overdueTasks: Result<List<TaskShortDto>>,
    val todayTasks: Result<List<TaskShortDto>>,
    val weekTasks: Result<List<TaskShortDto>>,
    val laterTasks: Result<List<TaskShortDto>>,
    val noDateTasks: Result<List<TaskShortDto>>,
    val isRefreshing: Boolean,
    val isError: Boolean
)
