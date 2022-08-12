package com.trialbot.taskoasis.domain

import com.trialbot.core_domain.GetTokenService

class LoginStatusUseCase(
    private val getTokenService: GetTokenService
) {

    fun isUserLoggedIn(): Boolean = getTokenService.checkIfTokenExists()
}