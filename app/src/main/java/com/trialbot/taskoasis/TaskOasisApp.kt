package com.trialbot.taskoasis

import android.app.Application
import com.trialbot.feature_auth.di.authModule
import com.trialbot.taskoasis.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TaskOasisApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@TaskOasisApp)
            modules(appModule, authModule)
        }
    }
}