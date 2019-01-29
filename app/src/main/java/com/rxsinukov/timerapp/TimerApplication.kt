package com.rxsinukov.timerapp

import android.app.Application
import com.rxsinukov.timerapp.di.ComponentsBuilders

class TimerApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        ComponentsBuilders.getInstance(this).initialize(this@TimerApplication)
    }
}
