package com.trialbot.feature_auth.domain.repository

import com.trialbot.feature_auth.data.model.Credentials

interface CredentialsRepository {

    fun saveCredentials(credentials: Credentials)

    fun getCredentials(): Credentials?
}