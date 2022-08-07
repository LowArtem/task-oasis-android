package com.trialbot.feature_auth.data.repository

import com.trialbot.core_model.User
import com.trialbot.core_model.exceptions.EmptyResponseBodyException
import com.trialbot.core_model.exceptions.WrongRemoteResponseException
import com.trialbot.core_utils.Result
import com.trialbot.feature_auth.data.model.LoginRequest
import com.trialbot.feature_auth.data.model.LoginResponse
import com.trialbot.feature_auth.data.model.RegisterRequest
import com.trialbot.feature_auth.data.model.toUser
import com.trialbot.feature_auth.data.remote.AuthDao
import com.trialbot.feature_auth.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val authDao: AuthDao
) : AuthRepository {
    override suspend fun login(loginRequest: LoginRequest): Result<User> {
        val response = authDao.login(loginRequest)
        return if (!response.isSuccessful) {
            when (response.code()) {
                400 -> {
                    Result.Error(WrongRemoteResponseException(response.body().toString()))
                }
                else -> {
                    Result.Error(WrongRemoteResponseException("Unknown error"))
                }
            }
        } else {
            val loginResponse: LoginResponse? = response.body()
            if (loginResponse == null) {
                Result.Error(EmptyResponseBodyException("Empty login response"))
            } else {
                Result.Success(loginResponse.toUser())
            }
        }
    }

    override suspend fun register(registerRequest: RegisterRequest): Result<User> {
        val response = authDao.register(registerRequest)
        return if (!response.isSuccessful) {
            when (response.code()) {
                400 -> {
                    Result.Error(WrongRemoteResponseException(response.body().toString()))
                }
                else -> {
                    Result.Error(WrongRemoteResponseException("Unknown error"))
                }
            }
        } else {
            login(LoginRequest(registerRequest.email, registerRequest.password))
        }
    }
}