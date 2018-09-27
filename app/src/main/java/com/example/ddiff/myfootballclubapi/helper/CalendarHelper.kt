package com.example.ddiff.myfootballclubapi.helper

import android.Manifest
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat

/**
 * Created by Diffa Dwi Desyawan on 25/9/18.
 * email : diffadwi1@gmail.com.
 */
class CalendarHelper {

    companion object {
        val REQUEST_CODE = 99
        fun requestCalendarReadWritePermission(caller: FragmentActivity?) {
            val permissionList = ArrayList<String>()

            if (ContextCompat.checkSelfPermission(caller!!, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.WRITE_CALENDAR)
            }

            if (ContextCompat.checkSelfPermission(caller, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.READ_CALENDAR)

            }

            if (permissionList.size > 0) {
                val permissionArray = arrayOfNulls<String>(permissionList.size)

                for (i in permissionList.indices) {
                    permissionArray[i] = permissionList[i]
                }

                ActivityCompat.requestPermissions(caller,
                        permissionArray,
                        REQUEST_CODE)
            }

        }

        fun haveCalendarReadWritePermissions(caller: FragmentActivity?): Boolean {
            var permissionCheck = ContextCompat.checkSelfPermission(caller!!,
                    Manifest.permission.READ_CALENDAR)

            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                permissionCheck = ContextCompat.checkSelfPermission(caller,
                        Manifest.permission.WRITE_CALENDAR)

                if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                    return true
                }
            }

            return false
        }

    }

}