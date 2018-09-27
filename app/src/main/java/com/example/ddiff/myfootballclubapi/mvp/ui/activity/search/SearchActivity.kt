package com.example.ddiff.myfootballclubapi.mvp.ui.activity.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.widget.Toast
import com.example.ddiff.myfootballclubapi.R
import com.example.ddiff.myfootballclubapi.adapter.MatchAdapter
import com.example.ddiff.myfootballclubapi.mvp.model.MatchModel
import com.example.ddiff.myfootballclubapi.mvp.presenter.search.SearchContract
import com.example.ddiff.myfootballclubapi.mvp.presenter.search.SearchPresenter
import com.example.ddiff.myfootballclubapi.mvp.ui.activity.detail.DetailActivity
import com.example.ddiff.myfootballclubapi.network.ApiService
import com.example.ddiff.myfootballclubapi.utils.AppScheduler
import com.example.ddiff.myfootballclubapi.utils.getKeyIntent
import com.example.ddiff.myfootballclubapi.utils.invisible
import com.example.ddiff.myfootballclubapi.utils.visible
import kotlinx.android.synthetic.main.activity_search.*
import org.jetbrains.anko.startActivity

/**
 * Created by Diffa Dwi Desyawan on 23/9/18.
 * email : diffadwi1@gmail.com.
 */
class SearchActivity : AppCompatActivity(), SearchContract.View {
    private lateinit var searchView: SearchView
    private lateinit var presenter: SearchContract.Presenter
    @SuppressLint("PrivateResource")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        supportActionBar?.title = resources.getString(R.string.search_match)
        progressBarSearch.invisible()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        searchView = menu?.findItem(R.id.search)?.actionView as SearchView
        searchView.isIconfiedByDefault
        searchView.queryHint = resources.getString(R.string.search_match)
        searchView.isFocusable = true
        presenter = SearchPresenter(this, ApiService(), AppScheduler())
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                presenter.searchTeam(newText)
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onLoadMatch(list: List<MatchModel>) {
        val adapter = MatchAdapter(list) {
            startActivity<DetailActivity>(getKeyIntent() to it)
        }
        rv_search.layoutManager = LinearLayoutManager(applicationContext)
        rv_search.adapter = adapter
    }

    override fun showProgress() {
        progressBarSearch.visible()
    }

    override fun showLoadErrorMessage(throwable: Throwable?) {
        Toast.makeText(applicationContext, "Failed to display list. \n ${throwable?.localizedMessage}", Toast.LENGTH_LONG).show()
    }

    override fun hideProgress() {
        progressBarSearch.invisible()
    }
}
