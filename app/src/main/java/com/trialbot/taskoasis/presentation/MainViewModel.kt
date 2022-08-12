package com.trialbot.taskoasis.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trialbot.taskoasis.domain.LoginStatusUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

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