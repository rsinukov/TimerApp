package com.rxsinukov.domain

import io.reactivex.schedulers.TestScheduler
import org.junit.Test
import java.util.concurrent.TimeUnit

class TimerTest {

    @Test
    fun `start should return configured instance`() {
        val scheduler = TestScheduler(1, TimeUnit.SECONDS)
        val timer = Timer(scheduler)
        val observer = timer.start(
            timeToCount = Time(5),
            period = Time(1),
            maxTime = Time(10)
        )

        val testObserver = observer.observeTime().test()
        scheduler.triggerActions()

        testObserver.assertValue(Time(5))
        scheduler.advanceTimeBy(20, TimeUnit.SECONDS)
        testObserver.assertValues(Time(5), Time(4), Time(3), Time(2), Time(1), Time(0))
    }
}
