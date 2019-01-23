package com.rxsinukov.timerapp.screens.timer

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.jakewharton.rxbinding3.view.clicks
import com.rxsinukov.common.rx.plusAssign
import com.rxsinukov.commonandroid.delegates.ActivityWithDelegate
import com.rxsinukov.timerapp.MviDelegate
import com.rxsinukov.timerapp.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class TimerActivity : ActivityWithDelegate() {

    @Inject
    lateinit var presenter: TimerPresenter

    private lateinit var label: TextView

    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        val delegate = MviDelegate(
            activity = this,
            disposable = disposable,
            componentClass = TimerComponent::class.java
        )
        addDelegate(delegate)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)

        delegate.component.inject(this)

        label = findViewById(R.id.activity_timer_time_label)
        val incButton = findViewById<Button>(R.id.activity_timer_inc_button)

        presenter.processIntentions(
            Observable.merge(
                Observable.just(TimerIntention.Initial),
                incButton.clicks().map { TimerIntention.IncreaseTimer }
            )
        )
    }

    override fun onStart() {
        super.onStart()
        disposable += presenter.states()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::render)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing) {
            presenter.destroy()
        }
    }

    private fun render(state: TimerState) {
        label.text = state.timeString
    }
}
