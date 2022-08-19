package com.trialbot.core_utils

import java.time.Instant

interface GetCurrentTimeInstant {

    fun getCurrentTime(): Instant
}

class GetCurrentTimeInstantDefault : GetCurrentTimeInstant {
    override fun getCurrentTime(): Instant = Instant.now()
}