package com.example.ddiff.myfootballclubapi.mvp.ui.fragment.match


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.Toast
import com.example.ddiff.myfootballclubapi.R
import com.example.ddiff.myfootballclubapi.adapter.MatchAdapter
import com.example.ddiff.myfootballclubapi.mvp.model.MatchModel
import com.example.ddiff.myfootballclubapi.mvp.presenter.lastmatch.MatchContract
import com.example.ddiff.myfootballclubapi.mvp.presenter.lastmatch.MatchPresenter
import com.example.ddiff.myfootballclubapi.mvp.ui.activity.detail.DetailActivity
import com.example.ddiff.myfootballclubapi.network.ApiService
import com.example.ddiff.myfootballclubapi.utils.AppScheduler
import com.example.ddiff.myfootballclubapi.utils.getKeyIntent
import com.example.ddiff.myfootballclubapi.utils.invisible
import com.example.ddiff.myfootballclubapi.utils.visible
import kotlinx.android.synthetic.main.fragment_last_match.*
import org.jetbrains.anko.support.v4.startActivity

/**
 * Created by Diffa Dwi Desyawan on 15/9/18.
 * email : diffadwi1@gmail.com.
 */
class LastMatchFragment : Fragment(), MatchContract.View {


    private lateinit var presenter: MatchPresenter
    private lateinit var rvMatch: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var leagueName: String
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val mRootView = inflater.inflate(R.layout.fragment_last_match, container, false)
        rvMatch = mRootView.findViewById(R.id.rv_fragment_match)
        progressBar = mRootView.findViewById(R.id.progressBar)

        return mRootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadPresenter()
        val spinnerItem = resources.getStringArray(R.array.leagueArray)
        val spinnerAdapter = ArrayAdapter(activity, android.R.layout.simple_spinner_dropdown_item, spinnerItem)

        spinnerMatch.adapter = spinnerAdapter

        spinnerMatch.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                leagueName = spinnerMatch.selectedItem.toString()
                when (leagueName) {
                    "English Premier League" -> presenter.getMatch("4328")
                    "German Bundesliga" -> presenter.getMatch("4331")
                    "Italian Serie A" -> presenter.getMatch("4332")
                    "French Ligue 1" -> presenter.getMatch("4334")
                    "Spanish La Liga" -> presenter.getMatch("4335")
                    "Netherlands Eredivisie" -> presenter.getMatch("4337")
                    else -> presenter.getMatch("4328")
                }
            }

        }
    }

    private fun loadPresenter() {
        val request = ApiService()
        val scheduler = AppScheduler()
        presenter = MatchPresenter(this, request, scheduler)
        presenter.getMatch("4328")
    }

    override fun onLoadClub(list: List<MatchModel>) {
        val adapter = MatchAdapter(list) {
            startActivity<DetailActivity>(getKeyIntent() to it)
        }
        rvMatch.layoutManager = LinearLayoutManager(activity)
        rvMatch.adapter = adapter
    }

    override fun showProgress() {
        progressBar.visible()
    }

    override fun showLoadErrorMessage(throwable: Throwable?) {
        Toast.makeText(context, "Failed to display list. \n ${throwable?.localizedMessage}", Toast.LENGTH_LONG).show()
    }

    override fun hideProgress() {
        progressBar.invisible()
    }


}

