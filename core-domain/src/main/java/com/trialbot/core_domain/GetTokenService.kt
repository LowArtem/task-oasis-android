package com.trialbot.core_domain

interface GetTokenService {

    fun getAuthToken(): String

    fun checkIfTokenExists(): Boolean
}