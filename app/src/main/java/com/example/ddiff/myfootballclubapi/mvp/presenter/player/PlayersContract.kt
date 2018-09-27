package com.example.ddiff.myfootballclubapi.mvp.presenter.player

import com.example.ddiff.myfootballclubapi.mvp.model.PlayerModel

interface PlayersContract {
    interface Presenter {
        fun getPlayers(id: String?)
    }

    interface View {
        fun onLoadPlayers(list: List<PlayerModel>)
        fun showLoadErrorMessage(throwable: Throwable?)
    }
}