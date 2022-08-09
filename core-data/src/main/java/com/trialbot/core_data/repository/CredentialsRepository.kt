package com.trialbot.core_data.repository

import com.trialbot.core_model.Credentials

interface CredentialsRepository {

    fun saveCredentials(credentials: Credentials)

    fun getCredentials(): Credentials?
}