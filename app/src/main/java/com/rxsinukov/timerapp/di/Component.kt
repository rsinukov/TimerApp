package com.rxsinukov.timerapp.di

import com.rxsinukov.common.mvi.MVIPresenter
import dagger.MapKey
import kotlin.reflect.KClass

/**
 * Base interface for ALL screen [dagger.Subcomponent] objects.
 */
interface MviComponent<Intention, State> {
    fun presenter(): MVIPresenter<Intention, State>
}

/**
 * Wrapper interface for [MviComponent] to allow Dagger map binding.
 */
interface ComponentBuilder<Intention, State, T : MviComponent<Intention, State>> {

    fun build(): T
}

/**
 * MapKey for binding [MviComponent].
 */
@MapKey
annotation class ComponentKey(val value: KClass<out MviComponent<*,*>>)
