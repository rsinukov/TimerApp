package com.rxsinukov.timerapp

import android.app.Application
import com.rxsinukov.timerapp.di.ComponentsHolder

class TimerApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        ComponentsHolder.getInstance(this).initialize(this@TimerApplication)
    }
}
