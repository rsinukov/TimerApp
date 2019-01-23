package com.rxsinukov.common.qualifiers

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ApplicationContext

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class IoScheduler

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ComputationScheduler

