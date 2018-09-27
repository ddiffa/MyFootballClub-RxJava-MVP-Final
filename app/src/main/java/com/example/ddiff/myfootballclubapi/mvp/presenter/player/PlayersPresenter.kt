package com.example.ddiff.myfootballclubapi.mvp.presenter.player

import com.example.ddiff.myfootballclubapi.network.ApiService
import com.example.ddiff.myfootballclubapi.utils.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class PlayersPresenter(private val playersView: PlayersContract.View,
                       val apiService: ApiService,
                       val schedulers: SchedulerProvider) : PlayersContract.Presenter {
    private val subscription = CompositeDisposable()

    override fun getPlayers(id: String?) {
        subscription.add(apiService.loadAllPlayers(id)
                .observeOn(schedulers.ui())
                .subscribeOn(schedulers.io())
                .subscribe(
                        {
                            playersView.onLoadPlayers(it.player)
                        },
                        {
                            playersView.showLoadErrorMessage(it)
                        }
                )
        )

    }

}