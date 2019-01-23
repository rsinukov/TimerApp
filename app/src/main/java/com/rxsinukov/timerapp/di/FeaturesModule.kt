package com.rxsinukov.timerapp.di

import com.rxsinukov.timerapp.screens.timer.TimerComponent
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(
    subcomponents = [
        TimerComponent::class
    ]
)
interface FeaturesModule {

    @Binds
    @IntoMap
    @ComponentKey(TimerComponent::class)
    fun timerComponentComponent(builder: TimerComponent.Builder): ComponentBuilder<*>
}
