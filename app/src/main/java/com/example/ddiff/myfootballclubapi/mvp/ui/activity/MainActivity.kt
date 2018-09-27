package com.example.ddiff.myfootballclubapi.mvp.ui.activity

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.example.ddiff.myfootballclubapi.R
import com.example.ddiff.myfootballclubapi.R.id.*
import com.example.ddiff.myfootballclubapi.mvp.ui.fragment.favorite.FavoriteFragment
import com.example.ddiff.myfootballclubapi.mvp.ui.fragment.match.MatchFragment
import com.example.ddiff.myfootballclubapi.mvp.ui.fragment.team.TeamFragment
import com.example.ddiff.myfootballclubapi.utils.ActivityUtil

/**
 * Created by Diffa Dwi Desyawan on 15/9/18.
 * email : diffadwi1@gmail.com.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.main_toolbar)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bot_navigation)
        setSupportActionBar(toolbar)
        supportActionBar?.title = resources.getString(R.string.app_name)
        bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                menu_match -> {
                    supportActionBar?.show()
                    ActivityUtil().loadFragment(MatchFragment(), supportFragmentManager)
                    item.isChecked = true
                }
                menu_team -> {
                    supportActionBar?.hide()
                    ActivityUtil().loadFragment(TeamFragment(), supportFragmentManager)
                    item.isChecked = true
                }
                menu_fav -> {
                    supportActionBar?.show()
                    ActivityUtil().loadFragment(FavoriteFragment(), supportFragmentManager)
                    item.isChecked = true
                }
            }
            true
        }
        bottomNav.selectedItemId = menu_match
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}

