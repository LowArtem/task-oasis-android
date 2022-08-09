package com.trialbot.feature_auth.util

fun String.validateAsEmail(): Boolean {
    val regexPattern = "^[a-zA-Z\\d_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z\\d.-]+$"
    val regex = regexPattern.toRegex()
    return regex.matches(this)
}

fun String.validateAsPassword(): Boolean = this.length >= 4