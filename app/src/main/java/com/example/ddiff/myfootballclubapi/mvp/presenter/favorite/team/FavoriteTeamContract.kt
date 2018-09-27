package com.example.ddiff.myfootballclubapi.mvp.presenter.favorite.team

import com.example.ddiff.myfootballclubapi.mvp.model.db.FavoriteTeamDB
import com.example.ddiff.myfootballclubapi.mvp.model.TeamModel

interface FavoriteTeamContract {
    interface Presenter {
        fun getTeam(favoriteDB: List<FavoriteTeamDB>)
    }

    interface View {
        fun showTeam(list: MutableList<TeamModel>)
        fun showLoadErrorMessage(throwable: Throwable?)
    }
}