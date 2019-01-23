package com.rxsinukov.domain

import com.rxsinukov.common.qualifiers.TimeScheduler
import dagger.Reusable
import io.reactivex.Scheduler
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@Reusable
class Timer @Inject constructor(
    @TimeScheduler private val timerScheduler: Scheduler
) {

    fun start(
        timeToCount: Time = Time(TimeUnit.MINUTES.toMillis(2)),
        period: Time = Time(TimeUnit.MILLISECONDS.toMillis(100)),
        maxTime: Time = Time(TimeUnit.MINUTES.toMillis(2))
    ): TimerObserver {
        return TimerObserver(
            maxTime = maxTime,
            period = period,
            timeToCount = timeToCount,
            timerScheduler = timerScheduler
        ).apply { start() }
    }
}

