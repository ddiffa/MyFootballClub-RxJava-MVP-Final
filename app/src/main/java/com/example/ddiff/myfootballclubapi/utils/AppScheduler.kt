package com.example.ddiff.myfootballclubapi.utils

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
/**
 * Created by Diffa Dwi Desyawan on 21/9/18.
 * email : diffadwi1@gmail.com.
 */
class AppScheduler : SchedulerProvider {
    override fun ui(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    override fun io(): Scheduler {
        return Schedulers.io()
    }
}