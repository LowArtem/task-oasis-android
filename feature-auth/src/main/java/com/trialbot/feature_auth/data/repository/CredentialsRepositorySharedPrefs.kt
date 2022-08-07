package com.trialbot.feature_auth.data.repository

import androidx.security.crypto.EncryptedSharedPreferences
import com.trialbot.feature_auth.data.model.Credentials
import com.trialbot.feature_auth.domain.repository.CredentialsRepository

class CredentialsRepositorySharedPrefs(private val sharedPrefs: EncryptedSharedPreferences) :
    CredentialsRepository {
    override fun saveCredentials(credentials: Credentials) {
        with(sharedPrefs.edit()) {
            putString(EMAIL_KEY, credentials.email)
            putString(PASSWORD_KEY, credentials.password)
            putString(TOKEN_KEY, credentials.token)
        }
    }

    override fun getCredentials(): Credentials? {
        val email = sharedPrefs.getString(EMAIL_KEY, null)
        val password = sharedPrefs.getString(PASSWORD_KEY, null)
        val token = sharedPrefs.getString(TOKEN_KEY, null)

        if (email.isNullOrBlank() || password.isNullOrBlank() || token.isNullOrBlank())
            return null

        return Credentials(email, password, token)
    }

    companion object {
        private const val EMAIL_KEY = "email_key"
        private const val PASSWORD_KEY = "password_key"
        private const val TOKEN_KEY = "token_key"
    }
}