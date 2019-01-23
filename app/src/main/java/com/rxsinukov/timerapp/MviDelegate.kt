package com.rxsinukov.timerapp

import android.app.Activity
import android.os.Bundle
import com.rxsinukov.commonandroid.delegates.Delegate
import com.rxsinukov.timerapp.di.ComponentBuilder
import com.rxsinukov.timerapp.di.ComponentsHolder
import com.rxsinukov.timerapp.di.MyComponent
import io.reactivex.disposables.CompositeDisposable
import java.util.*

class MviDelegate<Component : MyComponent>(
    private val activity: Activity,
    private val disposable: CompositeDisposable,
    private val componentClass: Class<Component>,
    private val componentCreator: (ComponentBuilder<Component>) -> Component = { it.build() }
) : Delegate() {

    private lateinit var uuid: String

    lateinit var component: Component

    override fun onCreate(state: Bundle?) {
        if (state == null) {
            uuid = UUID.randomUUID().toString()
        }
        component = ComponentsHolder.getInstance(activity).get(uuid, componentClass, componentCreator)
    }

    override fun onStop() {
        disposable.clear()
    }

    override fun onRetainCustomNonConfigurationInstance(instance: Any?) {
        uuid = instance as String
    }

    override fun getLastCustomNonConfigurationInstance(): Any = uuid
}
