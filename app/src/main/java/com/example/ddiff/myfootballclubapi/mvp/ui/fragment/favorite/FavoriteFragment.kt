package com.example.ddiff.myfootballclubapi.mvp.ui.fragment.favorite


import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ddiff.myfootballclubapi.R
import com.example.ddiff.myfootballclubapi.adapter.ViewPagerAdapter

/**
 * Created by Diffa Dwi Desyawan on 23/9/18.
 * email : diffadwi1@gmail.com.
 */
class FavoriteFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPager = view.findViewById<ViewPager>(R.id.view_pager)
        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout)
        val adapter = ViewPagerAdapter(childFragmentManager)
        setHasOptionsMenu(true)
        adapter.setFragment(FavoriteMatchFragment(), resources.getString(R.string.favorite_match))
        adapter.setFragment(FavoriteTeamsFragment(), resources.getString(R.string.favorite_team))
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
    }

}
