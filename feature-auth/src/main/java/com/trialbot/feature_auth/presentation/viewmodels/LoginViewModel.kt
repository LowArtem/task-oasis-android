package com.trialbot.feature_auth.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trialbot.core_utils.Result
import com.trialbot.feature_auth.data.model.LoginRequest
import com.trialbot.feature_auth.domain.use_case.AuthUseCase
import com.trialbot.feature_auth.presentation.events.AuthEvent
import com.trialbot.feature_auth.presentation.events.UiEvent
import com.trialbot.feature_auth.presentation.states.ErrorState
import com.trialbot.feature_auth.presentation.states.InputFieldState
import com.trialbot.feature_auth.util.validateAsEmail
import com.trialbot.feature_auth.util.validateAsPassword
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch


class LoginViewModel(
    private val authUseCase: AuthUseCase
) : ViewModel() {

    private val _email = mutableStateOf(InputFieldState())
    val email: State<InputFieldState> = _email

    private val _password = mutableStateOf(
        InputFieldState(
            isPasswordVisible = false
        )
    )
    val password: State<InputFieldState> = _password

    private val _uiEventsFlow = MutableSharedFlow<UiEvent>()
    val uiEventsFlow = _uiEventsFlow.asSharedFlow()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
        viewModelScope.launch {
            _uiEventsFlow.emit(
                UiEvent.ShowShackbar(
                    throwable.message ?: "Unknown error occurred"
                )
            )
        }
    }

    fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.EnteredEmail -> {
                _email.value = _email.value.copy(text = event.value)
                if (!email.value.text.validateAsEmail()) {
                    _email.value = email.value.copy(
                        validatingError = ErrorState(
                            message = "Invalid email",
                            isErrorOccurred = true
                        )
                    )
                } else {
                    _email.value = email.value.copy(
                        validatingError = ErrorState(
                            message = "",
                            isErrorOccurred = false
                        )
                    )
                }
            }
            is AuthEvent.EnteredPassword -> {
                _password.value = _password.value.copy(text = event.value)
                if (!password.value.text.validateAsPassword()) {
                    _password.value = password.value.copy(
                        validatingError = ErrorState(
                            message = "At least 4 characters",
                            isErrorOccurred = true
                        )
                    )
                } else {
                    _password.value = password.value.copy(
                        validatingError = ErrorState(
                            message = "",
                            isErrorOccurred = false
                        )
                    )
                }
            }
            is AuthEvent.PasswordVisibilityChanged -> {
                _password.value =
                    _password.value.copy(isPasswordVisible = !_password.value.isPasswordVisible)
            }
            is AuthEvent.Authenticate -> {
                if (email.value.text.validateAsEmail() && password.value.text.validateAsPassword()) {
                    viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
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
            is AuthEvent.NavigateNext -> {
                viewModelScope.launch(coroutineExceptionHandler) {
                    _uiEventsFlow.emit(UiEvent.NavigateToNext)
                }
            }
            else -> Unit
        }
    }
}