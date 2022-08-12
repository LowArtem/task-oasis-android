package com.trialbot.taskoasis.presentation

import androidx.lifecycle.ViewModel
import com.trialbot.taskoasis.domain.LoginStatusUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel(
    loginStatusUseCase: LoginStatusUseCase
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    val isUserLoggedIn = loginStatusUseCase.isUserLoggedIn()

    init {
        _isLoading.value = false
    }
}