package com.rxsinukov.timerapp.di

import android.app.Application
import android.content.Context
import androidx.annotation.VisibleForTesting
import timber.log.Timber
import java.util.*
import javax.inject.Inject
import javax.inject.Provider

/**
 * Uses runtime generated keys for storing components. Component will leave as long as key lives
 */
class ComponentsHolder {

    companion object {

        private val instance = ComponentsHolder()

        @JvmStatic // param is here to ensure that no one tries to get instance from BL
        fun getInstance(@Suppress("UNUSED_PARAMETER") context: Context) = instance
    }

    private val appComponentScope = AppComponent::class.java
    private val components = WeakHashMap<Any, MyComponent>()

    @Inject
    @VisibleForTesting
    @Suppress("MemberVisibilityCanBePrivate")
    lateinit var builders: Map<Class<out MyComponent>, @JvmSuppressWildcards Provider<ComponentBuilder<*>>>

    fun initialize(application: Application) {
        if (getComponent<AppComponent>(appComponentScope) != null) {
            throw IllegalStateException("Already initialized")
        }

        val appComponent = DaggerAppComponent.builder()
            .application(application)
            .build()

        putComponent(appComponentScope, appComponent)
        appComponent.inject(this)
    }

    fun appComponent() = get(appComponentScope, appComponentScope)

    fun <T : MyComponent> get(
        scope: Any,
        componentClass: Class<T>,
        creator: (ComponentBuilder<T>) -> T = { it.build() }
    ): T {
        getComponent<AppComponent>(appComponentScope)
            ?: throw IllegalStateException("No AppComponent - call Dagger.initialize()")

        val cached = getComponent<T>(scope)
        return if (cached == null) {
            @Suppress("UNCHECKED_CAST") val builder = builders[componentClass]?.get() as? ComponentBuilder<T>?
                ?: throw IllegalStateException("Component build for component ${componentClass.simpleName} not found")
            val component = creator(builder)
            putComponent(scope, component)
            component
        } else {
            cached
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T : MyComponent> getComponent(scope: Any): T? = components[scope] as T?

    private fun putComponent(scope: Any, component: MyComponent) {
        if (components[scope] != null) {
            Timber.w("Saving component for scope \"$scope\" while this scope already exists!")
        }
        components[scope] = component
    }
}
