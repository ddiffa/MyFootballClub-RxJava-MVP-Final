package com.example.ddiff.myfootballclubapi.mvp.presenter.team

import com.example.ddiff.myfootballclubapi.mvp.model.TeamModel

interface TeamContract {
    interface Presenter {
        fun getAllTeam(query: String)
        fun searchTeam(search: String?)
    }

    interface View {
        fun onLoadTeam(list: List<TeamModel>)
        fun showProgress()
        fun showLoadErrorMessage(throwable: Throwable?)
        fun hideProgress()
    }
}