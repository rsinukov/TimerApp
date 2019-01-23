package com.rxsinukov.timerapp.screens.timer

import com.rxsinukov.common.mvi.MVIInteractor
import com.rxsinukov.common.qualifiers.IoScheduler
import com.rxsinukov.domain.Timer
import com.rxsinukov.domain.TimerObserver
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.Scheduler
import java.util.concurrent.atomic.AtomicReference
import javax.inject.Inject

@TimerScope
class TimerInteractor @Inject constructor(
    private val timer: Timer,
    @IoScheduler private val scheduler: Scheduler
) : MVIInteractor<TimerAction, TimerResult> {

    private val observer = AtomicReference<TimerObserver>()

    private val increaseTimerProcessor: ObservableTransformer<in TimerAction.IncreaseTimer, out TimerResult> =
        ObservableTransformer { actions ->
            actions
                .flatMapCompletable {
                    Completable.fromAction { observer.get().incTime(it.diff) }
                }
                .toObservable()
        }

    private val initialProcessor: ObservableTransformer<in TimerAction.Initial, out TimerResult> =
        ObservableTransformer { actions ->
            actions.switchMap { action ->
                timer
                    .start(
                        timeToCount = action.timeToCount,
                        maxTime = action.maxTime,
                        period = action.period
                    )
                    .apply { observer.set(this) }
                    .observeTime()
                    .map { TimerResult.TimerUpdated(it) }
            }
        }

    override fun actionProcessor(): ObservableTransformer<in TimerAction, out TimerResult> =
        ObservableTransformer { actions ->
            actions
                .observeOn(scheduler)
                .publish { action ->
                    val observables = listOf(
                        action.ofType(TimerAction.LastState::class.java)
                            .map { TimerResult.LastState },
                        action.ofType(TimerAction.IncreaseTimer::class.java)
                            .compose(increaseTimerProcessor),
                        action.ofType(TimerAction.Initial::class.java)
                            .compose(initialProcessor)
                    )
                    Observable.merge(observables)
                }
        }
}
