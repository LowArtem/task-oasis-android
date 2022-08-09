package com.trialbot.core_data.di

import com.trialbot.core_data.repository.CredentialsRepository
import com.trialbot.core_data.repository.CredentialsRepositorySharedPrefs
import org.koin.dsl.module

val dataModule = module {

    single<CredentialsRepository> {
        CredentialsRepositorySharedPrefs(
            sharedPrefs = get()
        )
    }
}