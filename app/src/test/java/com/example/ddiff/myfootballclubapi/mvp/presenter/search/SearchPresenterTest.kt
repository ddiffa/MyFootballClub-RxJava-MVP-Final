package com.example.ddiff.myfootballclubapi.mvp.presenter.search

import com.example.ddiff.myfootballclubapi.mvp.model.MatchModel
import com.example.ddiff.myfootballclubapi.mvp.model.MatchResponse
import com.example.ddiff.myfootballclubapi.network.ApiService
import com.example.ddiff.myfootballclubapi.utils.SchedulerProvider
import com.example.ddiff.myfootballclubapi.utils.SchedulerProviderTest
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class SearchPresenterTest {
    @Mock
    lateinit var apiService: ApiService

    @Mock
    lateinit var matchView: SearchContract.View
    lateinit var presenter: SearchPresenter

    lateinit var matchResponse: MatchResponse

    lateinit var match: Observable<MatchResponse>

    private val event = mutableListOf<MatchModel>()
    lateinit var scheduler: SchedulerProvider

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        scheduler = SchedulerProviderTest()
        matchResponse = MatchResponse(event, event)
        match = Observable.just(matchResponse)
        presenter = SearchPresenter(matchView, apiService, scheduler)
        Mockito.`when`(apiService.getSearchMatch("barcelona")).thenReturn(match)
    }

    @Test
    fun searchTeam() {
        presenter.searchTeam("barcelona")
        Mockito.verify(matchView).onLoadMatch(event)
        Mockito.verify(matchView).showProgress()
        Mockito.verify(matchView).hideProgress()
    }
}