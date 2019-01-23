package com.rxsinukov.domain

import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import java.util.concurrent.TimeUnit


class TimerObserverTest {

    private val testScheduler = TestScheduler()

    @Before
    fun setUp() {
        testScheduler.advanceTimeTo(1, TimeUnit.SECONDS)
    }

    @Test
    fun `start should start emitting items from startTime to 0 with period`() {
        val observer = TimerObserver(
            timeToCount = Time(11),
            period = Time(2),
            maxTime = Time(10),
            timerScheduler = testScheduler
        )

        observer.start()

        val testObserver = observer.observeTime().test()

        testScheduler.triggerActions()
        testObserver.assertValue(Time(11))

        testScheduler.advanceTimeBy(2, TimeUnit.MILLISECONDS)
        testObserver.assertValues(Time(11), Time(9))

        testScheduler.advanceTimeBy(2, TimeUnit.MILLISECONDS)
        testObserver.assertValues(Time(11), Time(9), Time(7))

        testScheduler.advanceTimeBy(11, TimeUnit.MILLISECONDS)
        testObserver.assertValues(Time(11), Time(9), Time(7), Time(5), Time(3), Time(1), Time(0))
    }

    @Test
    fun `incTime should increase timer`() {
        val observer = TimerObserver(
            timeToCount = Time(10),
            period = Time(1),
            maxTime = Time(30),
            timerScheduler = testScheduler
        )

        observer.start()

        val testObserver = observer.observeTime().test()

        testScheduler.triggerActions()
        testObserver.assertValue(Time(10))

        testScheduler.advanceTimeBy(1, TimeUnit.MILLISECONDS)
        testObserver.assertValues(Time(10), Time(9))

        observer.incTime(Time(10))

        testScheduler.advanceTimeBy(1, TimeUnit.MILLISECONDS)
        testObserver.assertValues(Time(10), Time(9), Time(19), Time(18))
    }

    @Test
    fun `incTime should increase timer not more them maxTime`() {
        val observer = TimerObserver(
            timeToCount = Time(10),
            period = Time(1),
            maxTime = Time(15),
            timerScheduler = testScheduler
        )

        observer.start()

        val testObserver = observer.observeTime().test()

        testScheduler.triggerActions()
        testObserver.assertValue(Time(10))

        testScheduler.advanceTimeBy(1, TimeUnit.MILLISECONDS)
        testObserver.assertValues(Time(10), Time(9))

        observer.incTime(Time(10))

        testScheduler.advanceTimeBy(1, TimeUnit.MILLISECONDS)
        testObserver.assertValues(Time(10), Time(9), Time(15), Time(14))
    }
}
