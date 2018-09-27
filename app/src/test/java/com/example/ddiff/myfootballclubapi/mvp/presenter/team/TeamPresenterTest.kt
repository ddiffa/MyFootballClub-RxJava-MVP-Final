package com.example.ddiff.myfootballclubapi.mvp.presenter.team

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
import org.mockito.MockitoAnnotations

class TeamPresenterTest {
    @Mock
    lateinit var apiService: ApiService

    @Mock
    lateinit var teamView: TeamContract.View
    lateinit var presenter: TeamPresenter

    lateinit var teamResponse: TeamResponse

    lateinit var team: Observable<TeamResponse>

    private val teams = mutableListOf<TeamModel>()
    lateinit var scheduler: SchedulerProvider

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        scheduler = SchedulerProviderTest()
        teamResponse = TeamResponse(teams)
        team = Observable.just(teamResponse)
        presenter = TeamPresenter(teamView, apiService, scheduler)
        Mockito.`when`(apiService.loadAllTeam("4328")).thenReturn(team)
        Mockito.`when`(apiService.getSearchTeam("barcelona")).thenReturn(team)
    }

    @Test
    fun getAllTeam() {
        presenter.getAllTeam("4328")
        Mockito.verify(teamView).onLoadTeam(teams)
        Mockito.verify(teamView).showProgress()
        Mockito.verify(teamView).hideProgress()
    }

    @Test
    fun searchTeam() {
        presenter.searchTeam("barcelona")
        Mockito.verify(teamView).onLoadTeam(teams)
        Mockito.verify(teamView).showProgress()
        Mockito.verify(teamView).hideProgress()
    }
}