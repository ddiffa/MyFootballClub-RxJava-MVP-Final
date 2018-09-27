package com.example.ddiff.myfootballclubapi.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
/**
 * Created by Diffa Dwi Desyawan on 23/9/18.
 * email : diffadwi1@gmail.com.
 */
class ViewPagerAdapter(fm: FragmentManager?) :
        FragmentPagerAdapter(fm) {
    private var fragmentList = arrayListOf<Fragment>()
    private var titleList = arrayListOf<String>()
    fun setFragment(fragment: Fragment, title: String) {
        fragmentList.add(fragment)
        titleList.add(title)
    }

    override fun getItem(position: Int): Fragment = fragmentList[position]

    override fun getCount(): Int = fragmentList.size

    override fun getPageTitle(position: Int) = titleList[position]

}
