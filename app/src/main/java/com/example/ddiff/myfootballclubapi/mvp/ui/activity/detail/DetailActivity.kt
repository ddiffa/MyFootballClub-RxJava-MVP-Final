package com.example.ddiff.myfootballclubapi.mvp.ui.activity.detail

import android.annotation.SuppressLint
import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.ddiff.myfootballclubapi.R
import com.example.ddiff.myfootballclubapi.R.drawable.ic_add_to_favorites
import com.example.ddiff.myfootballclubapi.R.drawable.ic_added_to_favorites
import com.example.ddiff.myfootballclubapi.R.id.add_to_favorite
import com.example.ddiff.myfootballclubapi.R.menu.detail_menu
import com.example.ddiff.myfootballclubapi.helper.database
import com.example.ddiff.myfootballclubapi.mvp.model.MatchModel
import com.example.ddiff.myfootballclubapi.mvp.model.TeamModel
import com.example.ddiff.myfootballclubapi.mvp.model.db.FavoriteMatchDB
import com.example.ddiff.myfootballclubapi.mvp.presenter.detail.MatchDetailContract
import com.example.ddiff.myfootballclubapi.mvp.presenter.detail.MatchDetailPresenter
import com.example.ddiff.myfootballclubapi.network.ApiService
import com.example.ddiff.myfootballclubapi.utils.AppScheduler
import com.example.ddiff.myfootballclubapi.utils.getKeyIntent
import com.example.ddiff.myfootballclubapi.utils.getSimpleDate
import com.example.ddiff.myfootballclubapi.utils.getSimpleTime
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.layout_statistic_match.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

/**
 * Created by Diffa Dwi Desyawan on 21/9/18.
 * email : diffadwi1@gmail.com.
 */
class DetailActivity : AppCompatActivity(), MatchDetailContract.View {

    private lateinit var matchModel: MatchModel
    private lateinit var presenter: MatchDetailPresenter

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private lateinit var idEvent: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val request = ApiService()
        val scheduler = AppScheduler()
        matchModel = intent.getParcelableExtra(getKeyIntent())
        idEvent = matchModel.idEvent.toString()
        supportActionBar?.title = matchModel.strHomeTeam + " vs " + matchModel.strAwayTeam
        showMatch(matchModel)
        presenter = MatchDetailPresenter(this, request, scheduler)
        presenter.getTeams(matchModel.idHomeTeam, matchModel.idAwayTeam)
        favoriteState()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(detail_menu, menu)
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
            add_to_favorite -> {
                try {
                    if (isFavorite) removeFromFavorite() else addToFavorite()
                    isFavorite = !isFavorite
                    setFavorite()
                } catch (e: Exception) {
                    Snackbar.make(findViewById(R.id.li_detail), "Try Again", Snackbar.LENGTH_SHORT).show()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setFavorite() {
        if (isFavorite) menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_added_to_favorites)
        else menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_add_to_favorites)
    }

    private fun addToFavorite() = try {
        database.use {
            insert(FavoriteMatchDB.TABLE_FAVORITE,
                    FavoriteMatchDB.EVENT_ID to matchModel.idEvent,
                    FavoriteMatchDB.HOME_TEAM_ID to matchModel.idHomeTeam,
                    FavoriteMatchDB.AWAY_TEAM_ID to matchModel.idAwayTeam)
        }
        Snackbar.make(findViewById(R.id.li_detail), "Matches added to favorite", Snackbar.LENGTH_SHORT).show()
    } catch (e: SQLiteConstraintException) {
        Snackbar.make(findViewById(R.id.li_detail), "Matches can't add to favorite", Snackbar.LENGTH_SHORT).show()
    }

    private fun removeFromFavorite() {
        try {
            database.use {
                delete(FavoriteMatchDB.TABLE_FAVORITE,
                        "(EVENT_ID = {id})",
                        "id" to idEvent)
            }
            Snackbar.make(findViewById(R.id.li_detail),
                    "Matches remove from favorite", Snackbar.LENGTH_SHORT).show()
        } catch (e: SQLiteConstraintException) {
            Snackbar.make(findViewById(R.id.li_detail),
                    "Matches can't remove from favorite", Snackbar.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun showMatch(matchModel: MatchModel) {
        tv_club_name_away_detail.text = matchModel.strAwayTeam
        tv_club_name_home_detail.text = matchModel.strHomeTeam

        tv_score_away_club_detail.text = matchModel.intAwayScore
        tv_score_home_club_detail.text = matchModel.intHomeScore

        tv_date_detail.text = getSimpleDate(matchModel.strDate)
        tv_time_detail.text = getSimpleTime(matchModel.strTime)
        tv_goal_home.text = matchModel.strHomeGoalDetails
        tv_goal_away.text = matchModel.strAwayGoalDetails

        tv_shot_home.text = matchModel.intHomeShots
        tv_shot_away.text = matchModel.intAwayShots

        tv_gk_home.text = matchModel.strHomeLineupGoalkeeper
        tv_gk_away.text = matchModel.strAwayLineupGoalkeeper

        tv_defense_home.text = matchModel.strHomeLineupDefense
        tv_defense_away.text = matchModel.strAwayLineupDefense

        tv_midfield_home.text = matchModel.strHomeLineupMidfield
        tv_midfield_away.text = matchModel.strAwayLineupMidfield

        tv_foward_home.text = matchModel.strHomeLineupForward
        tv_foward_away.text = matchModel.strAwayLineupForward

        tv_sub_home.text = matchModel.strHomeLineupSubstitutes
        tv_sub_away.text = matchModel.strAwayLineupSubstitutes

        tv_yellow_home.text = matchModel.strHomeYellowCards
        tv_yellow_away.text = matchModel.strAwayYellowCards

        tv_red_home.text = matchModel.strHomeRedCards
        tv_red_away.text = matchModel.strAwayRedCards

    }

    override fun showLogo1(data: List<TeamModel>) {
        Picasso.get()
                .load(data[0].strTeamBadge)
                .placeholder(R.drawable.ic_loop_black_24dp)
                .into(img_home_club_detail)
    }

    override fun showLogo2(data: List<TeamModel>) {
        Picasso.get()
                .load(data[0].strTeamBadge)
                .placeholder(R.drawable.ic_loop_black_24dp)
                .into(img_away_club_detail)
    }

    override fun showLoadErrorMessage(throwable: Throwable?) {
        Toast.makeText(applicationContext, "Failed to display list. \n ${throwable?.localizedMessage}", Toast.LENGTH_LONG).show()
    }

    private fun favoriteState() {
        database.use {
            val result = select(FavoriteMatchDB.TABLE_FAVORITE)
                    .whereArgs("(EVENT_ID = {id})", "id" to idEvent)
            val favorite = result.parseList(classParser<FavoriteMatchDB>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }
}
