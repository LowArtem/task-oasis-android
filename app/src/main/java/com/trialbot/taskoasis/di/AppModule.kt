package com.trialbot.taskoasis.di

import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.trialbot.taskoasis.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

const val ENCRYPTED_SHARED_PREFERENCES_FILENAME = "encrypted_shared_prefs"

val appModule = module {

    single {
        EncryptedSharedPreferences.create(
            androidContext(),
            ENCRYPTED_SHARED_PREFERENCES_FILENAME,
            MasterKey.Builder(androidContext())
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        ) as EncryptedSharedPreferences
    }

    viewModel {
        MainViewModel()
    }
}