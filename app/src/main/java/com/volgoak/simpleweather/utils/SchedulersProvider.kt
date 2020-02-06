package com.volgoak.simpleweather.utils

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers

interface SchedulersProvider {
    val io: Scheduler
    val ui: Scheduler
    val computation: Scheduler
}

class SchedulersProviderImpl: SchedulersProvider {
    override val io: Scheduler = Schedulers.io()
    override val ui: Scheduler = AndroidSchedulers.mainThread()
    override val computation: Scheduler = Schedulers.computation()
}