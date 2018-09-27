package com.example.ddiff.myfootballclubapi.utils

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
/**
 * Created by Diffa Dwi Desyawan on 21/9/18.
 * email : diffadwi1@gmail.com.
 */
class SchedulerProviderTest: SchedulerProvider {
    override fun ui(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun io(): Scheduler {
        return Schedulers.trampoline()
    }
}