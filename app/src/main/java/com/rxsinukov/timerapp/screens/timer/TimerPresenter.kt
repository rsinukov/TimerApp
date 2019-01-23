package com.rxsinukov.timerapp.screens.timer

import com.rxsinukov.common.mvi.MVIBasePresenter
import com.rxsinukov.domain.Time
import com.rxsinukov.timerapp.TimeFormatter
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@TimerScope
class TimerPresenter @Inject constructor(
    interactor: TimerInteractor,
    private val timeFormatter: TimeFormatter
) : MVIBasePresenter<TimerIntention, TimerState, TimerAction, TimerResult>(interactor) {

    override val defaultState: TimerState
        get() = TimerState("")

    override val lastStateIntention: TimerIntention
        get() = TimerIntention.LastState

    override fun intentionToActionMapper(): (TimerIntention) -> TimerAction = {
        when (it) {
            TimerIntention.Initial -> TimerAction.Initial(
                timeToCount = Time.create(2, TimeUnit.MINUTES),
                period = Time.create(100, TimeUnit.MILLISECONDS),
                maxTime = Time.create(2, TimeUnit.MINUTES)
            )
            TimerIntention.LastState -> TimerAction.LastState
            TimerIntention.IncreaseTimer -> TimerAction.IncreaseTimer(Time.create(10, TimeUnit.SECONDS))
        }
    }

    override fun stateReducer(): (TimerState, TimerResult) -> TimerState = { prevState, result ->
        when (result) {
            is TimerResult.TimerUpdated -> prevState.copy(timeString = timeFormatter.format(result.time))
            TimerResult.LastState -> prevState
        }
    }
}
