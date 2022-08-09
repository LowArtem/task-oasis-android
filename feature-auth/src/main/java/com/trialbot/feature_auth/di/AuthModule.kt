package com.trialbot.feature_auth.di

import com.google.gson.GsonBuilder
import com.trialbot.core_model.constants.Constants
import com.trialbot.core_utils.ignoreAllSSLErrors
import com.trialbot.feature_auth.data.remote.AuthDao
import com.trialbot.feature_auth.data.repository.AuthRepositoryImpl
import com.trialbot.feature_auth.domain.repository.AuthRepository
import com.trialbot.feature_auth.domain.use_case.AuthUseCase
import com.trialbot.feature_auth.presentation.viewmodels.LoginViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val AUTH_OKHTTP_CLIENT = "auth_OkHttp_client"
const val AUTH_RETROFIT_CLIENT = "auth_retrofit_client"

val authModule = module {
    factory(named(AUTH_OKHTTP_CLIENT)) {
        OkHttpClient.Builder().apply {
            addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            ignoreAllSSLErrors()
        }.build()
    }

    single {
        GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .create()
    }

    factory(named(AUTH_RETROFIT_CLIENT)) {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(get(named(AUTH_OKHTTP_CLIENT)))
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
            .create(AuthDao::class.java)
    }

    single<AuthRepository> {
        AuthRepositoryImpl(
            authDao = get()
        )
    }

    factory {
        AuthUseCase(
            authRepository = get(),
            credentialsRepository = get()
        )
    }

    viewModel {
        LoginViewModel(
            authUseCase = get()
        )
    }
}