package com.rxsinukov.commonandroid.delegates

import android.os.Bundle

abstract class Delegate {

    open fun onCreate(state: Bundle?) {}

    open fun onStart() {}

    open fun onResume() {}

    open fun onPause() {}

    open fun onStop() {}

    open fun onDestroy(isFinishing: Boolean) {}

    open fun onSaveInstanceState(outState: Bundle) {}

    open fun onRetainCustomNonConfigurationInstance(any: Any?) {}

    open fun getLastCustomNonConfigurationInstance(): Any? = null
}
