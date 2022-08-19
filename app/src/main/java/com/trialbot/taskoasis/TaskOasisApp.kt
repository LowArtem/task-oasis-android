package com.trialbot.taskoasis

import android.app.Application
import com.trialbot.core_data.di.dataModule
import com.trialbot.core_utils.di.utilsModule
import com.trialbot.feature_auth.di.authModule
import com.trialbot.feature_tasks.di.tasksModule
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
            modules(appModule, dataModule, utilsModule, authModule, tasksModule)
        }
    }
}