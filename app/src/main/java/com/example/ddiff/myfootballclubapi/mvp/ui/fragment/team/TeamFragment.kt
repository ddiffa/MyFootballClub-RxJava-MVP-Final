package com.example.ddiff.myfootballclubapi.mvp.ui.fragment.team


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.Toast
import com.example.ddiff.myfootballclubapi.R
import com.example.ddiff.myfootballclubapi.adapter.TeamAdapter
import com.example.ddiff.myfootballclubapi.mvp.model.TeamModel
import com.example.ddiff.myfootballclubapi.mvp.presenter.team.TeamContract
import com.example.ddiff.myfootballclubapi.mvp.presenter.team.TeamPresenter
import com.example.ddiff.myfootballclubapi.mvp.ui.activity.teamdetail.TeamDetailActivity
import com.example.ddiff.myfootballclubapi.network.ApiService
import com.example.ddiff.myfootballclubapi.utils.AppScheduler
import com.example.ddiff.myfootballclubapi.utils.getKeyActivity
import com.example.ddiff.myfootballclubapi.utils.invisible
import com.example.ddiff.myfootballclubapi.utils.visible
import kotlinx.android.synthetic.main.fragment_team.*
import org.jetbrains.anko.support.v4.startActivity

/**
 * Created by Diffa Dwi Desyawan on 24/9/18.
 * email : diffadwi1@gmail.com.
 */

class TeamFragment : Fragment(), TeamContract.View, TeamAdapter.OnItemClickListener {


    private lateinit var teamPresenter: TeamContract.Presenter
    private var team: MutableList<TeamModel> = mutableListOf()
    private lateinit var leagueName: String
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val mRootView = inflater.inflate(R.layout.fragment_team, container, false)
        recyclerView = mRootView.findViewById(R.id.rv_team)
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar = view.findViewById(R.id.progressBarTeam)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val request = ApiService()
        val scheduler = AppScheduler()
        setHasOptionsMenu(true)
        teamPresenter = TeamPresenter(this, request, scheduler)
        teamPresenter.getAllTeam("4328")

        val spinnerItem = resources.getStringArray(R.array.leagueArray)
        val spinnerAdapter = ArrayAdapter(activity, android.R.layout.simple_spinner_dropdown_item, spinnerItem)

        spinner_team.adapter = spinnerAdapter

        spinner_team.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                leagueName = spinner_team.selectedItem.toString()
                when (leagueName) {
                    "English Premier League" -> teamPresenter.getAllTeam("4328")
                    "German Bundesliga" -> teamPresenter.getAllTeam("4331")
                    "Italian Serie A" -> teamPresenter.getAllTeam("4332")
                    "French Ligue 1" -> teamPresenter.getAllTeam("4334")
                    "Spanish La Liga" -> teamPresenter.getAllTeam("4335")
                    "Netherlands Eredivisie" -> teamPresenter.getAllTeam("4337")
                    else -> teamPresenter.getAllTeam("4328")
                }
            }

        }

        search_view.queryHint = resources.getString(R.string.search_team)
        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != "") {
                    teamPresenter.searchTeam(newText)
                } else {
                    spinner_team.setSelection(0)
                    teamPresenter.getAllTeam("4328")
                }
                return false
            }

        })

    }

    private fun loadSpinner() {

    }

    override fun onLoadTeam(list: List<TeamModel>) {
        team.clear()
        team.addAll(list)
        val layoutManager = GridLayoutManager(context, 4)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = TeamAdapter(list, context, this)
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

    override fun itemDetail(model: TeamModel) {
        startActivity<TeamDetailActivity>(getKeyActivity() to model)
    }

}
