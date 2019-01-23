package com.rxsinukov.timerapp.screens.timer

import com.rxsinukov.common.mvi.InitialIntention
import com.rxsinukov.domain.Time

sealed class TimerIntention {

    object Initial : TimerIntention(), InitialIntention
    object LastState : TimerIntention()
    object IncreaseTimer : TimerIntention()
}

sealed class TimerAction {

    data class Initial(val timeToCount: Time, val period: Time, val maxTime: Time) : TimerAction(), InitialIntention
    object LastState : TimerAction()
    data class IncreaseTimer(val diff: Time) : TimerAction()
}

sealed class TimerResult {

    data class TimerUpdated(val time: Time) : TimerResult()
    object LastState : TimerResult()
}

data class TimerState(
    val timeString: String
)
