package com.trialbot.feature_auth.presentation.states

import com.trialbot.core_utils.states.ErrorState

data class InputFieldState(
    val text: String = "",
    val isPasswordVisible: Boolean = true,
    val validatingError: ErrorState = ErrorState()
)