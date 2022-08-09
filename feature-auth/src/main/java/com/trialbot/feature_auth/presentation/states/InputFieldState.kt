package com.trialbot.feature_auth.presentation.states

data class InputFieldState(
    val text: String = "",
    val isPasswordVisible: Boolean = true,
    val validatingError: ErrorState = ErrorState()
)