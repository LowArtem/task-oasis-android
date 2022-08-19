package com.trialbot.core_utils.di

import com.trialbot.core_utils.GetCurrentTimeInstant
import com.trialbot.core_utils.GetCurrentTimeInstantDefault
import org.koin.dsl.module

val utilsModule = module {

    single<GetCurrentTimeInstant> {
        GetCurrentTimeInstantDefault()
    }
}