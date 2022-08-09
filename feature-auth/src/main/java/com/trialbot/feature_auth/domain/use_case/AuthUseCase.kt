package com.trialbot.feature_auth.domain.use_case

import com.trialbot.core_data.repository.CredentialsRepository
import com.trialbot.core_model.Credentials
import com.trialbot.core_model.User
import com.trialbot.core_utils.Result
import com.trialbot.feature_auth.data.model.LoginRequest
import com.trialbot.feature_auth.data.model.RegisterRequest
import com.trialbot.feature_auth.domain.repository.AuthRepository

class AuthUseCase(
    private val authRepository: AuthRepository,
    private val credentialsRepository: CredentialsRepository
) {

    suspend fun login(loginRequest: LoginRequest): Result<Nothing?> {
        val userResult = authRepository.login(loginRequest)
        saveCredentials(userResult)
        return when (userResult) {
            is Result.Success -> Result.Success(null)
            is Result.Loading -> Result.Loading
            is Result.Error -> Result.Error(userResult.exception)
        }
    }

    suspend fun register(registerRequest: RegisterRequest): Result<Nothing?> {
        val userResult = authRepository.register(registerRequest)
        saveCredentials(userResult)
        return when (userResult) {
            is Result.Success -> Result.Success(null)
            is Result.Loading -> Result.Loading
            is Result.Error -> Result.Error(userResult.exception)
        }
    }

    private fun saveCredentials(userResult: Result<User>) {
        if (userResult is Result.Success) {
            credentialsRepository.saveCredentials(
                Credentials(
                    email = userResult.data.email,
                    password = userResult.data.password,
                    token = userResult.data.token ?: ""
                )
            )
        }
    }
}