package com.rxsinukov.commonandroid.delegates

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

open class ActivityWithDelegate : AppCompatActivity() {

    private val delegates = mutableListOf<Delegate>()

    protected fun addDelegate(delegate: Delegate) {
        delegates += delegate
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        delegates.forEach { it.onCreate(savedInstanceState) }

        val lastInstance = lastCustomNonConfigurationInstance as? Map<*, *>
        if (lastInstance != null) {
            delegates.forEach { it.onRetainCustomNonConfigurationInstance(lastInstance[it.key]) }
        }
    }

    override fun onStart() {
        super.onStart()
        delegates.forEach { it.onStart() }
    }

    override fun onResume() {
        super.onResume()
        delegates.forEach { it.onResume() }
    }

    override fun onPause() {
        super.onPause()
        delegates.forEach { it.onPause() }
    }

    override fun onStop() {
        delegates.forEach { it.onStop() }
        super.onStop()
    }

    override fun onDestroy() {
        delegates.forEach { it.onDestroy(isFinishing) }
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        delegates.forEach { it.onSaveInstanceState(outState) }
        super.onSaveInstanceState(outState)
    }

    final override fun onRetainCustomNonConfigurationInstance(): Any? {
        return delegates.associateBy({ it.key }, { it.getLastCustomNonConfigurationInstance() })
    }

    private val Delegate.key
        get() = javaClass.canonicalName
}
