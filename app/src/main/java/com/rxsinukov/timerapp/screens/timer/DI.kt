package com.rxsinukov.timerapp.screens.timer

import com.rxsinukov.timerapp.di.ComponentBuilder
import com.rxsinukov.timerapp.di.MviComponent
import dagger.Subcomponent
import javax.inject.Scope


@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class TimerScope

@TimerScope
@Subcomponent
interface TimerComponent : MviComponent<TimerIntention, TimerState> {

    override fun presenter(): TimerPresenter

    fun inject(activity: TimerActivity)

    @Subcomponent.Builder
    interface Builder : ComponentBuilder<TimerIntention, TimerState, TimerComponent>
}
