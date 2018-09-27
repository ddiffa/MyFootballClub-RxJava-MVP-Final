package com.example.ddiff.myfootballclubapi.mvp.presenter.nextmatch

import com.example.ddiff.myfootballclubapi.network.ApiService
import com.example.ddiff.myfootballclubapi.utils.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class NextMatchPresenter(private val matchView: NextMatchContract.View,
                         val apiService: ApiService,
                         val schedulers: SchedulerProvider) : NextMatchContract.Presenter {

    private val subscription = CompositeDisposable()
    override fun getNextMatch(id : String) {
        matchView.showProgress()
        subscription.add(apiService.loadNextMatch(id)
                .observeOn(schedulers.ui())
                .subscribeOn(schedulers.io())
                .subscribe(
                        {
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