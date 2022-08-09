package com.trialbot.feature_auth.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trialbot.core_utils.Result
import com.trialbot.feature_auth.data.model.LoginRequest
import com.trialbot.feature_auth.domain.use_case.AuthUseCase
import com.trialbot.feature_auth.presentation.events.LoginEvent
import com.trialbot.feature_auth.presentation.events.UiEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch


data class ErrorState(
    val message: String = "",
    val isErrorOccurred: Boolean = false
)

data class InputFieldState(
    val text: String = "",
    val isHintVisible: Boolean = true,
    val isPasswordVisible: Boolean = true,
    var validatingError: ErrorState = ErrorState()
)

class LoginViewModel(
    private val authUseCase: AuthUseCase
) : ViewModel() {

    private val _email = mutableStateOf(InputFieldState())
    val email: State<InputFieldState> = _email

    private val _password = mutableStateOf(InputFieldState())
    val password: State<InputFieldState> = _password

    private val _uiEventsFlow = MutableSharedFlow<UiEvent>()
    val uiEventsFlow = _uiEventsFlow.asSharedFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EnteredEmail -> {
                _email.value = _email.value.copy(text = event.value)
            }
            is LoginEvent.EnteredPassword -> {
                _password.value = _password.value.copy(text = event.value)
            }
            is LoginEvent.PasswordVisibilityChanged -> {
                _password.value =
                    _password.value.copy(isPasswordVisible = !_password.value.isPasswordVisible)
            }
            is LoginEvent.Login -> {
                if (!isEmailValid(email.value.text)) {
                    _email.value.validatingError = _email.value.validatingError.copy(
                        message = "Invalid email",
                        isErrorOccurred = true
                    )
                } else {
                    _email.value.validatingError = _email.value.validatingError.copy(
                        isErrorOccurred = false
                    )
                }
                if (!isPasswordValid(password.value.text)) {
                    _password.value.validatingError = _password.value.validatingError.copy(
                        message = "Password should contain at least 4 characters",
                        isErrorOccurred = true
                    )
                } else {
                    _password.value.validatingError = _password.value.validatingError.copy(
                        isErrorOccurred = false
                    )
                }
                if (isEmailValid(email.value.text) && isPasswordValid(password.value.text)) {
                    viewModelScope.launch(Dispatchers.IO) {
                        val result = authUseCase.login(
                            LoginRequest(
                                email = email.value.text,
                                password = password.value.text
                            )
                        )
                        when (result) {
                            is Result.Error -> _uiEventsFlow.emit(
                                UiEvent.ShowShackbar(
                                    result.exception?.message ?: "Unknown error occurred"
                                )
                            )
                            is Result.Success -> _uiEventsFlow.emit(
                                UiEvent.NavigateToHome
                            )
                            Result.Loading -> Unit
                        }
                    }
                }
            }
            is LoginEvent.NavigateToSignUp -> {
                viewModelScope.launch {
                    _uiEventsFlow.emit(UiEvent.NavigateToSignUp)
                }
            }
        }
    }

    private fun isEmailValid(emailString: String): Boolean {
        val regexPattern = "^[a-zA-Z\\d_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z\\d.-]+$"
        val regex = regexPattern.toRegex()
        return regex.matches(emailString)
    }

    private fun isPasswordValid(passwordString: String): Boolean = passwordString.length >= 4
}