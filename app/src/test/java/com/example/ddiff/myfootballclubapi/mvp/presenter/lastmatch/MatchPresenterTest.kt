package com.example.ddiff.myfootballclubapi.mvp.presenter.lastmatch

import com.example.ddiff.myfootballclubapi.mvp.model.MatchModel
import com.example.ddiff.myfootballclubapi.mvp.model.MatchResponse
import com.example.ddiff.myfootballclubapi.network.ApiService
import com.example.ddiff.myfootballclubapi.utils.SchedulerProvider
import com.example.ddiff.myfootballclubapi.utils.SchedulerProviderTest
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
/**
 * Created by Diffa Dwi Desyawan on 23/9/18.
 * email : diffadwi1@gmail.com.
 * github : ddiffa
 */
class MatchPresenterTest {
    @Mock
    lateinit var matchView: MatchContract.View

    @Mock
    lateinit var apiService: ApiService

    lateinit var scheduler: SchedulerProvider

    lateinit var presenter: MatchPresenter

    lateinit var matcheResponse : MatchResponse

    lateinit var match: Observable<MatchResponse>

    private val events = mutableListOf<MatchModel>()

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        scheduler = SchedulerProviderTest()
        matcheResponse = MatchResponse(events,events)
        match = Observable.just(matcheResponse)
        presenter = MatchPresenter(matchView,apiService,scheduler)
        `when`(apiService.loadMatch("4328")).thenReturn(match)
    }
    @Test
    fun getMatch() {
        presenter.getMatch("4328")
        verify(matchView).showProgress()
        verify(matchView).onLoadClub(events)
        verify(matchView).hideProgress()
    }
}