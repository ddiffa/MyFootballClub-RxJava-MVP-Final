package com.example.ddiff.myfootballclubapi.mvp.presenter.detail

import com.example.ddiff.myfootballclubapi.network.ApiService
import com.example.ddiff.myfootballclubapi.utils.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Diffa Dwi Desyawan on 15/9/18.
 * email : diffadwi1@gmail.com.
 */
class MatchDetailPresenter(private val matchDetailView: MatchDetailContract.View,
                           val apiService: ApiService,
                           val schedulers: SchedulerProvider) : MatchDetailContract.Presenter {
    private val subscription = CompositeDisposable()

    override fun getTeams(idTeam1: String?, idTeam2: String?) {
        subscription.add(apiService.loadTeam(idTeam1)
                .observeOn(schedulers.ui())
                .subscribeOn(schedulers.io())
                .subscribe(
                        {
                            matchDetailView.showLogo1(it.teams)
                        },
                        {
                            matchDetailView.showLoadErrorMessage(it)
                        }
                )
        )
        subscription.add(apiService.loadTeam(idTeam2)
                .observeOn(schedulers.ui())
                .subscribeOn(schedulers.io())
                .subscribe(
                        {
                            matchDetailView.showLogo2(it.teams)
                        },
                        {
                            matchDetailView.showLoadErrorMessage(it)
                        }
                )
        )
    }

}