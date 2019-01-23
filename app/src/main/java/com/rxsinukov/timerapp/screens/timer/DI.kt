package com.rxsinukov.timerapp.screens.timer

import com.rxsinukov.timerapp.di.ComponentBuilder
import com.rxsinukov.timerapp.di.MyComponent
import dagger.Subcomponent
import javax.inject.Scope


@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class TimerScope

@TimerScope
@Subcomponent
interface TimerComponent : MyComponent {

    fun inject(activity: TimerActivity)

    @Subcomponent.Builder
    interface Builder : ComponentBuilder<TimerComponent>
}
