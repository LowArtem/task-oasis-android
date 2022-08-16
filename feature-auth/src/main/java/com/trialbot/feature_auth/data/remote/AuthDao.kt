package com.trialbot.feature_auth.data.remote

import com.trialbot.feature_auth.data.model.LoginRequest
import com.trialbot.feature_auth.data.model.LoginResponse
import com.trialbot.feature_auth.data.model.RegisterRequest
import com.trialbot.feature_auth.data.model.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthDao {

    @POST("./auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @POST("./auth/register")
    suspend fun register(
        @Body request: RegisterRequest
    ) : Response<RegisterResponse>
}