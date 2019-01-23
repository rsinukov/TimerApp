package com.rxsinukov.timerapp

import android.content.Context
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.rxsinukov.domain.Time
import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.Before
import org.junit.Test
import java.util.concurrent.TimeUnit

class TimeFormatterTest {

    private lateinit var timeFormatter: TimeFormatter

    @Before
    fun setUp() {
        val context = mock<Context>()
        whenever(context.getString(R.string.done)).thenReturn("DONE")
        timeFormatter = TimeFormatter(context)
    }

    @Test
    fun `should format zero as DONE`() {
        assertThat(timeFormatter.format(Time(0))).isEqualTo("DONE")
    }

    @Test
    fun `should format without minutes if less then 60 seconds`() {
        assertThat(timeFormatter.format(Time.create(59, TimeUnit.SECONDS))).isEqualTo("59:0")
        assertThat(timeFormatter.format(Time.create(10, TimeUnit.SECONDS))).isEqualTo("10:0")
    }

    @Test
    fun `should format with 2 digits in seconds if more then 60 seconds`() {
        assertThat(timeFormatter.format(Time.create(69, TimeUnit.SECONDS))).isEqualTo("1:09:0")
        assertThat(timeFormatter.format(Time.create(60, TimeUnit.SECONDS))).isEqualTo("1:00:0")
    }

    @Test
    fun `should format with 1 digit in seconds if less then 60 seconds`() {
        assertThat(timeFormatter.format(Time.create(9, TimeUnit.SECONDS))).isEqualTo("9:0")
        assertThat(timeFormatter.format(Time.create(1, TimeUnit.SECONDS))).isEqualTo("1:0")
    }

    @Test
    fun `should format with 1 digit for millis`() {
        assertThat(timeFormatter.format(Time.create(123, TimeUnit.MILLISECONDS))).isEqualTo("0:1")
    }
}
