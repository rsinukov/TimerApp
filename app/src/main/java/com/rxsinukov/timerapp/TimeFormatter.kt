package com.rxsinukov.timerapp

import android.content.Context
import com.rxsinukov.common.qualifiers.ApplicationContext
import com.rxsinukov.domain.Time
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class TimeFormatter @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun format(time: Time): String = when (time.millis) {
        0L -> context.getString(R.string.done)
        in 1 until (TimeUnit.MINUTES.toMillis(1)) -> "${seconds(time, twoDigits = false)}:${millis(time)}"
        else -> "${minutes(time)}:${seconds(time, twoDigits = true)}:${millis(time)}"
    }

    private fun millis(time: Time): String = "${time.millis % 1000 / 100}"

    private fun seconds(time: Time, twoDigits: Boolean): String = (if (twoDigits) "%02d" else "%d")
        .format(time.millis / 1000 % 60)

    private fun minutes(time: Time): String = "${time.millis / 1000 / 60}"
}
