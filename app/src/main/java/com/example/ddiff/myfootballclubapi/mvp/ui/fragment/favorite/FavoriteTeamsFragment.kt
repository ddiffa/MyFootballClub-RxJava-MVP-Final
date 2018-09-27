package com.example.ddiff.myfootballclubapi.mvp.ui.fragment.favorite

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.ddiff.myfootballclubapi.R
import com.example.ddiff.myfootballclubapi.adapter.TeamAdapter
import com.example.ddiff.myfootballclubapi.helper.database
import com.example.ddiff.myfootballclubapi.mvp.model.TeamModel
import com.example.ddiff.myfootballclubapi.mvp.model.db.FavoriteTeamDB
import com.example.ddiff.myfootballclubapi.mvp.presenter.favorite.team.FavoriteTeamContract
import com.example.ddiff.myfootballclubapi.mvp.presenter.favorite.team.FavoriteTeamPresenter
import com.example.ddiff.myfootballclubapi.mvp.ui.activity.teamdetail.TeamDetailActivity
import com.example.ddiff.myfootballclubapi.network.ApiService
import com.example.ddiff.myfootballclubapi.utils.AppScheduler
import com.example.ddiff.myfootballclubapi.utils.getKeyActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.swipeRefreshLayout

/**
 * Created by Diffa Dwi Desyawan on 23/9/18.
 * email : diffadwi1@gmail.com.
 */
class FavoriteTeamsFragment : Fragment(), AnkoComponent<Context>, FavoriteTeamContract.View, TeamAdapter.OnItemClickListener {


    private lateinit var rvMatch: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var presenter: FavoriteTeamContract.Presenter
    private var favoriteTeam: MutableList<TeamModel> = mutableListOf()
    private lateinit var favoriteListDB: List<com.example.ddiff.myfootballclubapi.mvp.model.db.FavoriteTeamDB>
    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        linearLayout {
            lparams(width = matchParent, height = matchParent)
            orientation = LinearLayout.VERTICAL
            padding = dip(16)

            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(R.color.colorPrimary)
                id = R.id.swipeFavTeam
                relativeLayout {
                    lparams(width = matchParent, height = wrapContent)
                    rvMatch = recyclerView {
                        id = R.id.rv_team_favorite
                        lparams(width = matchParent, height = wrapContent)
                    }
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(ctx))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter = FavoriteTeamPresenter(this, ApiService(), AppScheduler())
        showFavorite()
        swipeRefresh.onRefresh {
            showFavorite()
        }
    }

    fun showFavorite(): List<com.example.ddiff.myfootballclubapi.mvp.model.db.FavoriteTeamDB> {

        context?.database?.use {
            val result = select(FavoriteTeamDB.TABLE_TEAM_NAME)
            val favorite = result.parseList(classParser<com.example.ddiff.myfootballclubapi.mvp.model.db.FavoriteTeamDB>())
            favoriteListDB = favorite
            swipeRefresh.isRefreshing = false
        }
        presenter.getTeam(favoriteListDB)
        return favoriteListDB

    }

    override fun showTeam(list: MutableList<TeamModel>) {
        favoriteTeam.clear()
        favoriteTeam.addAll(list)
        val layoutManager = GridLayoutManager(context, 4)
        rvMatch.layoutManager = layoutManager
        rvMatch.adapter = TeamAdapter(favoriteTeam, context, this)
    }

    override fun showLoadErrorMessage(throwable: Throwable?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun itemDetail(model: TeamModel) {
        startActivity<TeamDetailActivity>(getKeyActivity() to model)
    }
}