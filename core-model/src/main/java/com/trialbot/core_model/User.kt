package com.trialbot.core_model

data class User(
    val username: String,
    val password: String,
    val email: String,
    val token: String?,
    val id: Int?
)