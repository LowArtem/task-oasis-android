package com.trialbot.core_data.di

import com.google.gson.GsonBuilder
import com.trialbot.core_data.local.GetTokenServiceImpl
import com.trialbot.core_data.network.AuthenticationInterceptor
import com.trialbot.core_data.repository.CredentialsRepository
import com.trialbot.core_data.repository.CredentialsRepositorySharedPrefs
import com.trialbot.core_domain.GetTokenService
import com.trialbot.core_model.constants.Constants
import com.trialbot.core_utils.ignoreAllSSLErrors
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {

    single<CredentialsRepository> {
        CredentialsRepositorySharedPrefs(
            sharedPrefs = get()
        )
    }

    single<GetTokenService> {
        GetTokenServiceImpl(get())
    }

    single {
        OkHttpClient.Builder().apply {
            addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            addInterceptor(AuthenticationInterceptor(get()))
            ignoreAllSSLErrors()
        }.build()
    }

    single {
        GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
            .create()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
    }
}