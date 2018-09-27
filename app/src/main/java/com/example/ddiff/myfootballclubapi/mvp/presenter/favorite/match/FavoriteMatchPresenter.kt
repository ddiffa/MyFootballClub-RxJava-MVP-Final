package com.example.ddiff.myfootballclubapi.mvp.presenter.favorite.match

import com.example.ddiff.myfootballclubapi.mvp.model.db.FavoriteMatchDB
import com.example.ddiff.myfootballclubapi.mvp.model.MatchModel
import com.example.ddiff.myfootballclubapi.network.ApiService
import com.example.ddiff.myfootballclubapi.utils.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class FavoriteMatchPresenter(private val favView: FavoriteMatchContract.View,
                             val apiService: ApiService,
                             val schedulers: SchedulerProvider) : FavoriteMatchContract.Presenter{
    val subscription = CompositeDisposable()
    override fun getMatch(favoriteDB: List<FavoriteMatchDB>) {
        val list : MutableList<MatchModel> = mutableListOf()
        for (fav in favoriteDB){
            subscription.add(apiService.getEventById(fav.idEvent)
                    .observeOn(schedulers.ui())
                    .subscribeOn(schedulers.io())
                    .subscribe({
                        list.add(it.events[0])
                        favView.showMatch(list)
                    },{
                        favView.showLoadErrorMessage(it)
                    }))
        }
        if(list.isEmpty()){
            favView.showMatch(list)
        }
    }
}