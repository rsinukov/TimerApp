package com.rxsinukov.domain

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit
import kotlin.math.min

class TimerObserver internal constructor(
    private val maxTime: Time,
    private val period: Time,
    private val timeToCount: Time,
    private val timerScheduler: Scheduler
) {

    private val timeTrigger = BehaviorSubject.create<Time>().toSerialized()

    private val timerObservable: Observable<Time>

    private var currentTime: Time = timeToCount

    init {
        timerObservable = timeTrigger
            .switchMap { startTime ->
                Observable
                    .interval(0, period.millis, TimeUnit.MILLISECONDS, timerScheduler)
                    .map { startTime - period * it }
                    .takeWhile { it.millis > 0 }
                    .concatWith(Single.just(Time(0)))
            }
            .doOnNext { currentTime = it }
            .publish()
            .refCount()
    }

    internal fun start() {
        timeTrigger.onNext(currentTime)
    }

    fun observeTime(): Observable<Time> = timerObservable

    fun incTime(diff: Time) {
        updateTime(currentTime + diff)
    }

    private fun updateTime(time: Time) {
        val timeToSet = normalise(time.millis)
        timeTrigger.onNext(Time(timeToSet))
    }

    private fun normalise(timeMs: Long): Long {
        if (timeMs < 0) {
            throw IllegalArgumentException("Time can not be less then zero, was: $timeMs")
        }
        return min(maxTime.millis, timeMs)
    }
}
