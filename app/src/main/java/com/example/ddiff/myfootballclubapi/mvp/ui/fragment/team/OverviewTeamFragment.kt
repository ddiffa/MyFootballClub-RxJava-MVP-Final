package com.example.ddiff.myfootballclubapi.mvp.ui.fragment.team


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ddiff.myfootballclubapi.R
import com.example.ddiff.myfootballclubapi.mvp.model.TeamModel
import kotlinx.android.synthetic.main.fragment_overview_team.*

/**
 * Created by Diffa Dwi Desyawan on 24/9/18.
 * email : diffadwi1@gmail.com.
 */

class OverviewTeamFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_overview_team, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val team: TeamModel? = arguments?.getParcelable("teams")
        tv_detail_overview.text = team?.strDescriptionEN
    }

}
