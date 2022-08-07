package com.trialbot.feature_auth.domain.repository

import com.trialbot.core_model.User
import com.trialbot.core_utils.Result
import com.trialbot.feature_auth.data.model.LoginRequest
import com.trialbot.feature_auth.data.model.RegisterRequest

interface AuthRepository {

    suspend fun login(loginRequest: LoginRequest): Result<User>

    suspend fun register(registerRequest: RegisterRequest): Result<User>
}