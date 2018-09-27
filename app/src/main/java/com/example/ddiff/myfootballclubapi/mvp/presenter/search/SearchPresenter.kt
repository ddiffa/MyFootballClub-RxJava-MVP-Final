package com.example.ddiff.myfootballclubapi.mvp.presenter.search

import com.example.ddiff.myfootballclubapi.network.ApiService
import com.example.ddiff.myfootballclubapi.utils.AppScheduler
import com.example.ddiff.myfootballclubapi.utils.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class SearchPresenter(val sView : SearchContract.View,
                      val apiService: ApiService,
                      val scheduler: SchedulerProvider): SearchContract.Presenter {
    private val subscription = CompositeDisposable()

    override fun searchTeam(search: String?) {
        sView.showProgress()
        subscription.add(apiService.getSearchMatch(search)
                .observeOn(scheduler.ui())
                .subscribeOn(scheduler.io())
                .subscribe({
                    sView.onLoadMatch(it.event)
                    sView.hideProgress()
                },{
                    sView.showLoadErrorMessage(it)
                }))
    }
}