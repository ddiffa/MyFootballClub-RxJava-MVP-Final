package com.example.ddiff.myfootballclubapi.mvp.presenter.detail

import com.example.ddiff.myfootballclubapi.mvp.model.TeamModel
import com.example.ddiff.myfootballclubapi.mvp.model.TeamResponse
import com.example.ddiff.myfootballclubapi.network.ApiService
import com.example.ddiff.myfootballclubapi.utils.SchedulerProvider
import com.example.ddiff.myfootballclubapi.utils.SchedulerProviderTest
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
/**
 * Created by Diffa Dwi Desyawan on 23/9/18.
 * email : diffadwi1@gmail.com.
 * github : ddiffa
 */
class MatchDetailPresenterTest {
    @Mock
    lateinit var matchView: MatchDetailContract.View

    @Mock
    lateinit var apiService: ApiService

    lateinit var scheduler: SchedulerProvider

    lateinit var presenter: MatchDetailPresenter

    lateinit var teamResponse: TeamResponse

    lateinit var match: Observable<TeamResponse>

    private val events = mutableListOf<TeamModel>()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        scheduler = SchedulerProviderTest()
        teamResponse = TeamResponse(events)
        match = Observable.just(teamResponse)
        presenter = MatchDetailPresenter(matchView, apiService, scheduler)
        `when`(apiService.loadTeam("133600")).thenReturn(match)
    }

    @Test
    fun getTeams() {
        presenter.getTeams("133600", "133600")
        Mockito.verify(matchView).showLogo1(events)
        Mockito.verify(matchView).showLogo2(events)
    }
}