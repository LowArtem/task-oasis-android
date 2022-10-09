package com.trialbot.feature_tasks.presentation.state

import com.trialbot.core_model.Task
import com.trialbot.core_utils.Result
import com.trialbot.feature_tasks.presentation.viewmodels.AddEditTaskScreenTitle

data class AddEditUiState(
    val screenTitle: AddEditTaskScreenTitle,
    val task: Result<Task>,
    val isError: Boolean
)
