package com.example.ddiff.myfootballclubapi.mvp.presenter.detail

import com.example.ddiff.myfootballclubapi.mvp.model.TeamModel

/**
 * Created by Diffa Dwi Desyawan on 15/9/18.
 * email : diffadwi1@gmail.com.
 */
interface MatchDetailContract {
    interface Presenter {
        fun getTeams(idTeam1: String?, idTeam2: String?)
    }

    interface View {
        fun showLogo1(data: List<TeamModel>)
        fun showLogo2(data: List<TeamModel>)
        fun showLoadErrorMessage(throwable: Throwable?)
    }
}