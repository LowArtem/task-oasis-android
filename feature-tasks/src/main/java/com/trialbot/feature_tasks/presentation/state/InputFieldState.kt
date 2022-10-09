package com.trialbot.feature_tasks.presentation.state

import com.trialbot.core_utils.states.ErrorState

data class InputFieldState(
    val text: String = "",
    val validatingError: ErrorState = ErrorState()
)
