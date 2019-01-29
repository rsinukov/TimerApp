package com.rxsinukov.timerapp.di

import android.app.Application
import android.content.Context
import androidx.annotation.VisibleForTesting
import javax.inject.Inject
import javax.inject.Provider

class ComponentsBuilders {

    companion object {

        private val instance = ComponentsBuilders()

        @JvmStatic // param is here to ensure that no one tries to get instance from BL
        fun getInstance(@Suppress("UNUSED_PARAMETER") context: Context) = instance
    }

    lateinit var appComponent: AppComponent
        private set

    @Inject
    @VisibleForTesting
    @Suppress("MemberVisibilityCanBePrivate")
    lateinit var builders: Map<Class<out MviComponent<*, *>>, @JvmSuppressWildcards Provider<ComponentBuilder<*, *, *>>>

    fun initialize(application: Application) {
        if (::appComponent.isInitialized) {
            throw IllegalStateException("Already initialized")
        }

        appComponent = DaggerAppComponent.builder()
            .application(application)
            .build()
        appComponent.inject(this)
    }

    fun <I, S, C : MviComponent<I, S>> build(
        componentClass: Class<C>,
        creator: (ComponentBuilder<I, S, C>) -> C = { it.build() }
    ): C {
        if (::appComponent.isInitialized.not()) {
            throw IllegalStateException("No AppComponent - call Dagger.initialize()")
        }


        @Suppress("UNCHECKED_CAST") val builder = builders[componentClass]?.get() as? ComponentBuilder<I, S, C>?
            ?: throw IllegalStateException("Component build for component ${componentClass.simpleName} not found")
        return creator(builder)
    }
}
