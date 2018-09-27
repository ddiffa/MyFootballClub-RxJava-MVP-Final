package com.example.ddiff.myfootballclubapi.mvp.ui.fragment.favorite


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.example.ddiff.myfootballclubapi.R
import com.example.ddiff.myfootballclubapi.adapter.MatchAdapter
import com.example.ddiff.myfootballclubapi.helper.database
import com.example.ddiff.myfootballclubapi.mvp.model.MatchModel
import com.example.ddiff.myfootballclubapi.mvp.model.db.FavoriteMatchDB
import com.example.ddiff.myfootballclubapi.mvp.presenter.favorite.match.FavoriteMatchContract
import com.example.ddiff.myfootballclubapi.mvp.presenter.favorite.match.FavoriteMatchPresenter
import com.example.ddiff.myfootballclubapi.mvp.ui.activity.detail.DetailActivity
import com.example.ddiff.myfootballclubapi.network.ApiService
import com.example.ddiff.myfootballclubapi.utils.AppScheduler
import com.example.ddiff.myfootballclubapi.utils.getKeyIntent
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
class FavoriteMatchFragment : Fragment(), AnkoComponent<Context>,
        FavoriteMatchContract.View {


    private lateinit var rvMatch: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var favoriteListDB: List<com.example.ddiff.myfootballclubapi.mvp.model.db.FavoriteMatchDB>
    private var favoriteMatch: MutableList<MatchModel> = mutableListOf()
    private lateinit var presenter: FavoriteMatchContract.Presenter
    private lateinit var adapter: MatchAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(ctx))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        presenter = FavoriteMatchPresenter(this, ApiService(), AppScheduler())
        showFavorite()
        swipeRefresh.onRefresh {
            showFavorite()
        }
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        linearLayout {
            lparams(width = matchParent, height = matchParent)
            orientation = LinearLayout.VERTICAL
            padding = dip(16)

            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(R.color.colorPrimary)
                id = R.id.swipeFav
                relativeLayout {
                    lparams(width = matchParent, height = wrapContent)
                    rvMatch = recyclerView {
                        id = R.id.rv_match_favorite
                        lparams(width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(ctx)
                    }
                }
            }
        }
    }

    fun showFavorite(): List<com.example.ddiff.myfootballclubapi.mvp.model.db.FavoriteMatchDB> {

        context?.database?.use {
            val result = select(FavoriteMatchDB.TABLE_FAVORITE)
            val favorite = result.parseList(classParser<com.example.ddiff.myfootballclubapi.mvp.model.db.FavoriteMatchDB>())
            favoriteListDB = favorite
            swipeRefresh.isRefreshing = false
        }
        presenter.getMatch(favoriteListDB)
        return favoriteListDB

    }

    override fun showMatch(list: MutableList<MatchModel>) {
        favoriteMatch.clear()
        favoriteMatch.addAll(list)
        adapter = MatchAdapter(favoriteMatch) {
            startActivity<DetailActivity>(getKeyIntent() to it)
        }
        rvMatch.adapter = adapter
    }

    override fun showLoadErrorMessage(throwable: Throwable?) {
        Toast.makeText(context, "Failed to display list. \n ${throwable?.localizedMessage}", Toast.LENGTH_LONG).show()
    }

}

