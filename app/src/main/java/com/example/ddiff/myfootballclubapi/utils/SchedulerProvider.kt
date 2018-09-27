package com.example.ddiff.myfootballclubapi.utils

import io.reactivex.Scheduler

/**
 * Created by Diffa Dwi Desyawan on 21/9/18.
 * email : diffadwi1@gmail.com.
 */
interface SchedulerProvider {
        fun ui(): Scheduler
        fun io(): Scheduler
}