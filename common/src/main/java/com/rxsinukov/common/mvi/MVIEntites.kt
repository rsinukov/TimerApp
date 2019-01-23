package com.rxsinukov.common.mvi

import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.disposables.Disposable

interface MVIPresenter<in Intention, State> {
    fun processIntentions(intentions: Observable<out Intention>): Disposable
    fun states(): Observable<State>
    fun destroy()
}

interface MVIInteractor<in Action, out Result> {
    fun actionProcessor(): ObservableTransformer<in Action, out Result>
}

interface InitialIntention
