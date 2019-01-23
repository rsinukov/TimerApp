package com.rxsinukov.domain

import java.util.concurrent.TimeUnit

data class Time(val millis: Long) {

    companion object {
        fun create(time: Long, timeUnit: TimeUnit): Time = Time(timeUnit.toMillis(time))
    }

    operator fun plus(other: Time): Time = Time(this.millis + other.millis)

    operator fun minus(other: Time): Time = Time(this.millis - other.millis)

    operator fun times(multiplier: Long): Time = Time(this.millis * multiplier)
}
