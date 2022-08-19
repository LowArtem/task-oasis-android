package com.trialbot.core_utils.states

data class ErrorState(
    val message: String = "",
    val isErrorOccurred: Boolean = false
)