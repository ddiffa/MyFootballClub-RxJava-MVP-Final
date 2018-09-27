package com.example.ddiff.myfootballclubapi.utils

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import com.example.ddiff.myfootballclubapi.R
/**
 * Created by Diffa Dwi Desyawan on 15/9/18.
 * email : diffadwi1@gmail.com.
 */
class ActivityUtil {

    fun loadFragment(fragment: Fragment, fragmentManager: FragmentManager) {
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_container, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}
