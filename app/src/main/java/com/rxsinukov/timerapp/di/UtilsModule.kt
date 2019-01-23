package com.rxsinukov.timerapp.di

import com.rxsinukov.common.qualifiers.ComputationScheduler
import com.rxsinukov.common.qualifiers.IoScheduler
import com.rxsinukov.common.qualifiers.TimeScheduler
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@Module
object UtilsModule {

    @JvmStatic
    @Provides
    @IoScheduler
    fun provideIoScheduler() = Schedulers.io()

    @JvmStatic
    @Provides
    @ComputationScheduler
    fun provideComputationScheduler() = Schedulers.computation()

    @JvmStatic
    @Provides
    @TimeScheduler
    fun provideTimeScheduler() = AndroidSchedulers.mainThread()
}
