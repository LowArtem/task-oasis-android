package com.trialbot.feature_auth.util

fun String.validateAsEmail(): Boolean {
    val regexPattern = "^[a-zA-Z\\d_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z\\d.-]+$"
    val regex = regexPattern.toRegex()
    return regex.matches(this)
}

fun String.validateAsPassword(): Boolean = this.length >= 4

fun String.validateAsUsername(): Boolean {
    val regexPattern = "^[a-zA-Z0-9_]*$"
    val regex = regexPattern.toRegex()

    return this.length in 4..29 && regex.matches(this)
}