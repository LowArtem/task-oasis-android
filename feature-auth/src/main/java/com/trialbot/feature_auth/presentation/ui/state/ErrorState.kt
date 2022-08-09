package com.trialbot.feature_auth.presentation.ui.state

data class ErrorState(
    val message: String = "",
    val isErrorOccurred: Boolean = false
)