package com.example.ddiff.myfootballclubapi.mvp.presenter.favorite.team

import com.example.ddiff.myfootballclubapi.mvp.model.db.FavoriteTeamDB
import com.example.ddiff.myfootballclubapi.mvp.model.TeamModel
import com.example.ddiff.myfootballclubapi.network.ApiService
import com.example.ddiff.myfootballclubapi.utils.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class FavoriteTeamPresenter(private val favView: FavoriteTeamContract.View,
                            val apiService: ApiService,
                            val schedulers: SchedulerProvider) : FavoriteTeamContract.Presenter {
    val subscription = CompositeDisposable()

    override fun getTeam(favoriteDB: List<FavoriteTeamDB>) {
        val list: MutableList<TeamModel> = mutableListOf()
        for (fav in favoriteDB) {
            subscription.add(apiService.loadTeam(fav.idTeam)
                    .observeOn(schedulers.ui())
                    .subscribeOn(schedulers.io())
                    .subscribe({
                        list.add(it.teams[0])
                        favView.showTeam(list)
                    }, {
                        favView.showLoadErrorMessage(it)
                    }))
        }
        if (list.isEmpty()) {
            favView.showTeam(list)
        }

    }


}