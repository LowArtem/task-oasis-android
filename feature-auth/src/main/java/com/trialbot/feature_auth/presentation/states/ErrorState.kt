package com.trialbot.feature_auth.presentation.states

data class ErrorState(
    val message: String = "",
    val isErrorOccurred: Boolean = false
)