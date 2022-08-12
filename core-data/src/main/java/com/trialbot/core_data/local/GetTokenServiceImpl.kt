package com.trialbot.core_data.local

import com.trialbot.core_data.repository.CredentialsRepository
import com.trialbot.core_domain.GetTokenService

class GetTokenServiceImpl(
    private val credentialsRepository: CredentialsRepository
) : GetTokenService {

    /**
     * Gets user's authorization token, with which you have to make requests to the api
     * @throws [IllegalStateException] when token is null. Token cannot be null in this stage.
     */
    override fun getAuthToken(): String {
        val credentials = credentialsRepository.getCredentials()
            ?: throw IllegalStateException("Auth token is null. User should have been logged in here")

        return credentials.token
    }
}