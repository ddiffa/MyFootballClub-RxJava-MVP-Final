package com.example.ddiff.myfootballclubapi.mvp.presenter.favorite.match

import com.example.ddiff.myfootballclubapi.mvp.model.db.FavoriteMatchDB
import com.example.ddiff.myfootballclubapi.mvp.model.MatchModel
import com.example.ddiff.myfootballclubapi.mvp.model.MatchResponse
import com.example.ddiff.myfootballclubapi.network.ApiService
import com.example.ddiff.myfootballclubapi.utils.SchedulerProvider
import com.example.ddiff.myfootballclubapi.utils.SchedulerProviderTest
import io.reactivex.Observable
import org.junit.Test

import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class FavoriteMatchPresenterTest {
    @Mock
    lateinit var apiService: ApiService

    @Mock
    lateinit var matchView: FavoriteMatchContract.View
    lateinit var presenter: FavoriteMatchPresenter

    lateinit var matchResponse: MatchResponse

    lateinit var match: Observable<MatchResponse>
    private  var favoriteList =mutableListOf<FavoriteMatchDB>()
    private val events = mutableListOf<MatchModel>()
    lateinit var scheduler: SchedulerProvider

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        scheduler = SchedulerProviderTest()
        matchResponse = MatchResponse(events,events)
        match = Observable.just(matchResponse)
        presenter = FavoriteMatchPresenter(matchView, apiService, scheduler)
        Mockito.`when`(apiService.getEventById("4328")).thenReturn(match)
    }
    @Test
    fun getMatch() {
        presenter.getMatch(favoriteList)
        Mockito.verify(matchView).showMatch(events)
    }
}