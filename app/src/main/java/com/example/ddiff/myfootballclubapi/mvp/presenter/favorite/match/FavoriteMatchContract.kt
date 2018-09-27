package com.example.ddiff.myfootballclubapi.mvp.presenter.favorite.match

import com.example.ddiff.myfootballclubapi.mvp.model.db.FavoriteMatchDB
import com.example.ddiff.myfootballclubapi.mvp.model.MatchModel

interface FavoriteMatchContract {
    interface Presenter {
        fun getMatch(favoriteDB: List<FavoriteMatchDB>)
    }

    interface View {
        fun showMatch(list: MutableList<MatchModel>)
        fun showLoadErrorMessage(throwable: Throwable?)
    }
}