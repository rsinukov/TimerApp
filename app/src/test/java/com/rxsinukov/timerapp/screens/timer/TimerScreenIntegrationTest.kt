package com.rxsinukov.timerapp.screens.timer

import android.content.Context
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.rxsinukov.domain.Time
import com.rxsinukov.domain.Timer
import com.rxsinukov.timerapp.R
import com.rxsinukov.timerapp.TimeFormatter
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import io.reactivex.subjects.PublishSubject
import org.junit.Before
import org.junit.Test
import java.util.concurrent.TimeUnit

class TimerScreenIntegrationTest {

    private lateinit var presenter: TimerPresenter
    private lateinit var timeFormatter: TimeFormatter

    private val scheduler = TestScheduler()

    @Before
    fun setUp() {
        scheduler.advanceTimeTo(1, TimeUnit.SECONDS)

        val interactor = TimerInteractor(Timer(scheduler), Schedulers.trampoline())

        val context = mock<Context>()
        whenever(context.getString(R.string.done)).thenReturn("DONE")
        timeFormatter = TimeFormatter(context)

        presenter = TimerPresenter(interactor, timeFormatter)
    }

    @Test
    fun `on initial intention should subscribe to timer updates with start time of 2 minutes and period 100ms`() {
        val states = presenter.states().test()
        presenter.processIntentions(Observable.just(TimerIntention.Initial))

        scheduler.triggerActions()
        states.assertValue(TimerState("2:00:0"))

        scheduler.advanceTimeBy(2, TimeUnit.MINUTES)
        for ((index, time) in (TimeUnit.MINUTES.toMillis(2) downTo 0 step 100).withIndex()) {
            states.assertValueAt(index, TimerState(timeFormatter.format(Time(time))))
        }
    }

    @Test
    fun `on increaseTimer intention should increase timer for 10 seconds`() {
        val states = presenter.states().test()
        val intentions = PublishSubject.create<TimerIntention>()
        presenter.processIntentions(intentions)

        intentions.onNext(TimerIntention.Initial)
        scheduler.triggerActions()
        states.assertValue(TimerState("2:00:0"))

        scheduler.advanceTimeBy(1, TimeUnit.MINUTES)
        states.assertValueAt(states.valueCount() - 1, TimerState("1:00:0"))

        intentions.onNext(TimerIntention.IncreaseTimer)
        scheduler.triggerActions()
        states.assertLastValue(TimerState("1:10:0"))
        scheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        states.assertLastValue(TimerState("1:09:0"))
    }
}

fun <T> TestObserver<T>.assertLastValue(value: T) = assertValueAt(valueCount() - 1, value)
