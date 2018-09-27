package com.example.ddiff.myfootballclubapi.mvp.presenter.search

import com.example.ddiff.myfootballclubapi.mvp.model.MatchModel
import com.example.ddiff.myfootballclubapi.mvp.model.TeamModel

interface SearchContract {
    interface Presenter {
        fun searchTeam(search: String?)
    }

    interface View {
        fun onLoadMatch(list: List<MatchModel>)
        fun showProgress()
        fun showLoadErrorMessage(throwable: Throwable?)
        fun hideProgress()
    }
}