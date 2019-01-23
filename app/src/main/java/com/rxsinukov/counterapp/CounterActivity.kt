package com.rxsinukov.counterapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.rxsinukov.domain.Time
import com.rxsinukov.domain.Timer
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

class CounterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_counter)

        val timer = Timer(AndroidSchedulers.mainThread())
        val observer = timer.start(
            timeToCount = Time.create(100, TimeUnit.SECONDS),
            period = Time.create(100, TimeUnit.MILLISECONDS),
            maxTime = Time.create(2, TimeUnit.MINUTES)
        )

        val label = findViewById<TextView>(R.id.activity_counter_time_label)
        findViewById<TextView>(R.id.activity_counter_inc_button)
            .apply { setOnClickListener { observer.incTime(Time.create(10, TimeUnit.SECONDS)) } }


        observer.observeTime()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { label.setText("${it.millis / 60 / 1000}:${it.millis / 1000 % 60}:${it.millis % 1000 / 100}") }
    }
}
