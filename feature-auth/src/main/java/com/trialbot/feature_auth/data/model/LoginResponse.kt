package com.trialbot.feature_auth.data.model

import com.trialbot.core_model.User
import java.time.Instant

data class LoginResponse(
    val username: String,
    val password: String,
    val token: String,
    val email: String,
    val registrationDate: Instant,
    val level: Int,
    val experience: Long,
    val money: Long,
    val id: Int
)

fun LoginResponse.toUser(): User = User(username, password, email, token, id)
