package com.example.ddiff.myfootballclubapi.mvp.presenter.team

import com.example.ddiff.myfootballclubapi.network.ApiService
import com.example.ddiff.myfootballclubapi.utils.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class TeamPresenter(val teamView: TeamContract.View,
                    val apiService: ApiService,
                    val schedulers: SchedulerProvider) : TeamContract.Presenter {


    private val subscription = CompositeDisposable()
    override fun getAllTeam(query :String) {
        teamView.showProgress()
        subscription.add(apiService.loadAllTeam(query)
                .observeOn(schedulers.ui())
                .subscribeOn(schedulers.io())
                .subscribe(
                        {
                            teamView.onLoadTeam(it.teams)
                            teamView.hideProgress()
                        },
                        {
                            teamView.showLoadErrorMessage(it)
                        }
                )
        )

    }

    override fun searchTeam(search: String?) {
        teamView.showProgress()
        subscription.add(apiService.getSearchTeam(search)
                .observeOn(schedulers.ui())
                .subscribeOn(schedulers.io())
                .subscribe({
                    teamView.onLoadTeam(it.teams)
                    teamView.hideProgress()
                },{
                    teamView.showLoadErrorMessage(it)
                }))
    }

}