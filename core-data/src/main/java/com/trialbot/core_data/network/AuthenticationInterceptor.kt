package com.trialbot.core_data.network

import com.trialbot.core_domain.GetTokenService
import okhttp3.Interceptor
import okhttp3.Response

class AuthenticationInterceptor(
    private val getAuthTokenService: GetTokenService
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder()
            .header("Authorization", "Bearer ${getAuthTokenService.getAuthToken()}")
            .build()

        return chain.proceed(request)
    }
}