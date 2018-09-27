package com.example.ddiff.myfootballclubapi.mvp.presenter.nextmatch

import com.example.ddiff.myfootballclubapi.mvp.model.MatchModel

interface NextMatchContract {
    interface Presenter {
        fun getNextMatch(id : String)
    }

    interface View {
        fun onLoadClub(list: List<MatchModel>)
        fun showProgress()
        fun showLoadErrorMessage(throwable: Throwable?)
        fun hideProgress()
    }
}