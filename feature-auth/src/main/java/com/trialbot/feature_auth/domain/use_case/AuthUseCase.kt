package com.trialbot.feature_auth.domain.use_case

import com.trialbot.core_model.User
import com.trialbot.core_utils.Result
import com.trialbot.feature_auth.data.model.Credentials
import com.trialbot.feature_auth.data.model.LoginRequest
import com.trialbot.feature_auth.data.model.RegisterRequest
import com.trialbot.feature_auth.domain.repository.AuthRepository
import com.trialbot.feature_auth.domain.repository.CredentialsRepository

class AuthUseCase(
    private val authRepository: AuthRepository,
    private val credentialsRepository: CredentialsRepository
) {
    suspend fun login(loginRequest: LoginRequest): Result<User> {
        val userResult = authRepository.login(loginRequest)
        saveCredentials(userResult)
        return userResult
    }

    suspend fun register(registerRequest: RegisterRequest): Result<User> {
        val userResult = authRepository.register(registerRequest)
        saveCredentials(userResult)
        return userResult
    }

    private fun saveCredentials(userResult: Result<User>) {
        if (userResult is Result.Success) {
            credentialsRepository.saveCredentials(Credentials(
                email = userResult.data.email,
                password = userResult.data.password,
                token = userResult.data.token ?: ""
            ))
        }
    }
}