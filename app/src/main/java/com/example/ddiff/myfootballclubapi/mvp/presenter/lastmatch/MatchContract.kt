package com.example.ddiff.myfootballclubapi.mvp.presenter.lastmatch

import com.example.ddiff.myfootballclubapi.mvp.model.MatchModel
/**
 * Created by Diffa Dwi Desyawan on 15/9/18.
 * email : diffadwi1@gmail.com.
 */
interface MatchContract {

    interface Presenter {
        fun getMatch(id : String)
    }

    interface View {
        fun onLoadClub(list: List<MatchModel>)
        fun showProgress()
        fun showLoadErrorMessage(throwable: Throwable?)
        fun hideProgress()
    }
}