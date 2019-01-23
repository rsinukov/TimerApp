package com.rxsinukov.timerapp.di

import dagger.MapKey
import kotlin.reflect.KClass

/**
 * Base interface for ALL screen [dagger.Subcomponent] objects.
 */
interface MyComponent

/**
 * Wrapper interface for [Component] to allow Dagger map binding.
 */
interface ComponentBuilder<T : MyComponent> {

    fun build(): T
}

/**
 * MapKey for binding [Component].
 */
@MapKey
annotation class ComponentKey(val value: KClass<out MyComponent>)
