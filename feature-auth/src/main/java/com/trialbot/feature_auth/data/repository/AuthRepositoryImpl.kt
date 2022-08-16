package com.trialbot.feature_auth.data.repository

import android.util.Log
import com.trialbot.core_model.User
import com.trialbot.core_model.constants.Constants.APP_LOGGING_TAG
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

        Log.d(APP_LOGGING_TAG, "Login response code -> ${response.code()}")

        return if (!response.isSuccessful) {
            when (response.code()) {
                in 400..499 -> {
                    val message = (response.body()?.toString() ?: "").ifBlank {
                        "Wrong credentials"
                    }

                    Result.Error(WrongRemoteResponseException(message))
                }
                else -> {
                    val message = (response.body()?.toString() ?: "").ifBlank {
                        "Server internal error"
                    }

                    Result.Error(WrongRemoteResponseException("Unknown error: $message"))
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

        Log.d(APP_LOGGING_TAG, "Register response code -> ${response.code()}")

        return if (!response.isSuccessful) {
            when (response.code()) {
                in 400..499 -> {
                    val message = (response.body()?.text ?: "").ifBlank {
                        "User with this email or username is already exist"
                    }

                    Result.Error(WrongRemoteResponseException(message))
                }
                else -> {
                    val message = (response.body()?.text ?: "").ifBlank {
                        "Server internal error"
                    }

                    Result.Error(WrongRemoteResponseException("Unknown error: $message"))
                }
            }
        } else {
            login(LoginRequest(registerRequest.email, registerRequest.password))
        }
    }
}