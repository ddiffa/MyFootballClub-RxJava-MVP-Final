package com.example.ddiff.myfootballclubapi.utils

import android.annotation.SuppressLint
import android.view.View
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Diffa Dwi Desyawan on 15/9/18.
 * email : diffadwi1@gmail.com.
 */
fun getKeyIntent() = "match"

fun getKeyActivity() = "activity"
fun getNewSDF() = "EEEE, MMM d, yyyy"
fun getOldSdf() = "yyyy-MM-dd"

@SuppressLint("SimpleDateFormat")
fun getSimpleDate(date: String?): String? {
    val oldSdf = SimpleDateFormat(getOldSdf())
    val newSdf = SimpleDateFormat(getNewSDF(), Locale.getDefault())
    val newDate = newSdf.format(oldSdf.parse(date))
    return newDate
}

@SuppressLint("SimpleDateFormat")
fun getSimpleTime(time: String?): String? {
    if(time!=null) {
        val sdf = SimpleDateFormat("hh:mm:ss")
        val newSdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        val newTime = newSdf.format(sdf.parse(time))
        return newTime
    }
    return ""
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}