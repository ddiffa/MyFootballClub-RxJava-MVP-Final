package com.example.ddiff.myfootballclubapi.mvp.ui.activity.teamdetail

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.ddiff.myfootballclubapi.R
import com.example.ddiff.myfootballclubapi.adapter.ViewPagerAdapter
import com.example.ddiff.myfootballclubapi.helper.database
import com.example.ddiff.myfootballclubapi.mvp.model.TeamModel
import com.example.ddiff.myfootballclubapi.mvp.model.db.FavoriteTeamDB
import com.example.ddiff.myfootballclubapi.mvp.ui.fragment.player.PlayerFragment
import com.example.ddiff.myfootballclubapi.mvp.ui.fragment.team.OverviewTeamFragment
import com.example.ddiff.myfootballclubapi.utils.getKeyActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_team_detail.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.toast

/**
 * Created by Diffa Dwi Desyawan on 24/9/18.
 * email : diffadwi1@gmail.com.
 */
class TeamDetailActivity : AppCompatActivity() {
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private lateinit var team: TeamModel
    private lateinit var id: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detail)

        setSupportActionBar(toolbar)

        team = intent.getParcelableExtra(getKeyActivity())
        id = team.idTeam
        supportActionBar?.title = ""
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        onLoadImage()
        val adapter = ViewPagerAdapter(supportFragmentManager)
        val bundle = Bundle()
        bundle.putParcelable("teams", team)
        val teamFragment = OverviewTeamFragment()
        val playerFragment = PlayerFragment()
        teamFragment.arguments = bundle
        playerFragment.arguments = bundle
        adapter.setFragment(teamFragment, resources.getString(R.string.team_overview))
        adapter.setFragment(playerFragment, resources.getString(R.string.players))
        view_pager_team.adapter = adapter
        tab_layout_team.setupWithViewPager(view_pager_team)
        favoriteState()
    }

    private fun onLoadImage() {
        Picasso.get()
                .load(team.strTeamBadge)
                .placeholder(R.drawable.ic_loop_white_24dp)
                .into(img_team_detail)
        tv_name_stadium.text = team.strStadium
        tv_date.text = team.intFormedYear
        tv_name_team.text = team.strTeam
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.add_to_favorite -> {
                try {
                    if (isFavorite) removeFromFavorite() else addToFavorite()
                    isFavorite = !isFavorite
                    setFavorite()
                } catch (e: Exception) {
                    Snackbar.make(findViewById(R.id.parent_layout), "Try Again!", Snackbar.LENGTH_SHORT).show()
                }
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }


    }

    private fun removeFromFavorite() {
        try {
            database.use {
                delete(FavoriteTeamDB.TABLE_TEAM_NAME,
                        "(TEAM_ID = {id})",
                        "id" to id)
            }

            Snackbar.make(findViewById(R.id.parent_layout), "Team remove favorite", Snackbar.LENGTH_SHORT).show()
        } catch (e: SQLiteConstraintException) {

            Snackbar.make(findViewById(R.id.parent_layout), "Team can't remove favorite", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun addToFavorite() = try {
        database.use {
            insert(FavoriteTeamDB.TABLE_TEAM_NAME,
                    FavoriteTeamDB.TEAM_ID to team.idTeam
            )
            Snackbar.make(findViewById(R.id.parent_layout), "Team added to favorite", Snackbar.LENGTH_SHORT).show()
        }
    } catch (e: SQLiteConstraintException) {

        Snackbar.make(findViewById(R.id.parent_layout), "Team can't added to favorite", Snackbar.LENGTH_SHORT).show()
    }


    private fun setFavorite() {
        if (isFavorite) menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorites)
        else menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorites)
    }

    private fun favoriteState() {
        database.use {
            val result = select(FavoriteTeamDB.TABLE_TEAM_NAME)
                    .whereArgs("(TEAM_ID = {id})", "id" to id)
            val favorite = result.parseList(classParser<FavoriteTeamDB>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }
}
