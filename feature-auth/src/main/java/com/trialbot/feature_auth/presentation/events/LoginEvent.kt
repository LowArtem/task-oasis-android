package com.trialbot.feature_auth.presentation.events

sealed class LoginEvent {
    data class EnteredEmail(val value: String) : LoginEvent()
    data class EnteredPassword(val value: String) : LoginEvent()
    object PasswordVisibilityChanged : LoginEvent()
    object Login : LoginEvent()
    object NavigateToSignUp : LoginEvent()
}

sealed class UiEvent {
    data class ShowShackbar(val message: String) : UiEvent()
    object NavigateToHome : UiEvent()
    object NavigateToSignUp : UiEvent()
}
