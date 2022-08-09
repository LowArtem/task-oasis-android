package com.trialbot.feature_auth.presentation.events

sealed class AuthEvent {
    data class EnteredEmail(val value: String) : AuthEvent()
    data class EnteredPassword(val value: String) : AuthEvent()
    object PasswordVisibilityChanged : AuthEvent()
    object Authenticate : AuthEvent()
    object NavigateNext : AuthEvent()
}

sealed class UiEvent {
    data class ShowShackbar(val message: String) : UiEvent()
    object NavigateToHome : UiEvent()
    object NavigateToNext : UiEvent()
}
