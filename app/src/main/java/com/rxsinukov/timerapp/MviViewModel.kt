package com.rxsinukov.timerapp

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.rxsinukov.timerapp.di.ComponentBuilder
import com.rxsinukov.timerapp.di.ComponentsHolder
import com.rxsinukov.timerapp.di.MviComponent

/**
 * Wraps generation and saving instance of a component through config changes
 */
class MviViewModel : ViewModel() {

    private lateinit var componentInstance: MviComponent<*, *>

    fun <Intention, State, Component : MviComponent<Intention, State>> component(
        activity: Activity,
        componentClass: Class<Component>,
        componentCreator: (ComponentBuilder<Intention, State, Component>) -> Component = { it.build() }
    ): Component {
        if (!::componentInstance.isInitialized) {
            componentInstance = ComponentsHolder.getInstance(activity).build(componentClass, componentCreator)
        }
        @Suppress("UNCHECKED_CAST")
        return componentInstance as Component
    }

    override fun onCleared() {
        componentInstance.presenter().destroy()
    }
}
