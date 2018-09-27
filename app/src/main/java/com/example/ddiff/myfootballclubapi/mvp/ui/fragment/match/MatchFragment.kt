package com.example.ddiff.myfootballclubapi.mvp.ui.fragment.match


import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.*
import com.example.ddiff.myfootballclubapi.R
import com.example.ddiff.myfootballclubapi.adapter.ViewPagerAdapter
import com.example.ddiff.myfootballclubapi.mvp.ui.activity.search.SearchActivity
import org.jetbrains.anko.support.v4.startActivity

/**
 * Created by Diffa Dwi Desyawan on 23/9/18.
 * email : diffadwi1@gmail.com.
 */
class MatchFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_match, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPager = view.findViewById<ViewPager>(R.id.view_pager)
        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout_match)

        val adapter = ViewPagerAdapter(childFragmentManager)
        setHasOptionsMenu(true)
        adapter.setFragment(NextMatchFragment(), resources.getString(R.string.next_match))
        adapter.setFragment(LastMatchFragment(), resources.getString(R.string.match))
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_action_search, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_search -> {
                startActivity<SearchActivity>()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
