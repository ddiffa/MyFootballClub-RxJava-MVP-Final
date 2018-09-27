package com.example.ddiff.myfootballclubapi.mvp.presenter.lastmatch

import com.example.ddiff.myfootballclubapi.network.ApiService
import com.example.ddiff.myfootballclubapi.utils.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Diffa Dwi Desyawan on 15/9/18.
 * email : diffadwi1@gmail.com.
 */
class MatchPresenter(private val matchView: MatchContract.View,
                     val apiService: ApiService,
                     val schedulers: SchedulerProvider) : MatchContract.Presenter {
    private val subscription = CompositeDisposable()

    override fun getMatch(id : String) {
        matchView.showProgress()
        subscription.add(apiService.loadMatch(id)
                .observeOn(schedulers.ui())
                .subscribeOn(schedulers.io())
                .subscribe({
                    matchView.onLoadClub(it.events)
                    matchView.hideProgress()
                },
                        {
                            matchView.showLoadErrorMessage(it)
                        }
                )
        )

    }


}




