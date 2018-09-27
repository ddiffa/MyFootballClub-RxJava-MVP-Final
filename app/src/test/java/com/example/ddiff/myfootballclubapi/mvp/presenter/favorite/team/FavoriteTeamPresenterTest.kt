package com.example.ddiff.myfootballclubapi.mvp.presenter.favorite.team

import com.example.ddiff.myfootballclubapi.mvp.model.db.FavoriteTeamDB
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

class FavoriteTeamPresenterTest {
    @Mock
    lateinit var apiService: ApiService

    @Mock
    lateinit var matchView: FavoriteTeamContract.View
    lateinit var presenter: FavoriteTeamPresenter

    lateinit var matchResponse: TeamResponse

    lateinit var match: Observable<TeamResponse>
    private var favoriteList = mutableListOf<FavoriteTeamDB>()
    private val events = mutableListOf<TeamModel>()
    lateinit var scheduler: SchedulerProvider

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        scheduler = SchedulerProviderTest()
        matchResponse = TeamResponse(events)
        match = Observable.just(matchResponse)
        presenter = FavoriteTeamPresenter(matchView, apiService, scheduler)
        Mockito.`when`(apiService.loadTeam("133604")).thenReturn(match)
    }

    @Test
    fun getTeam() {
        presenter.getTeam(favoriteList)
        Mockito.verify(matchView).showTeam(events)
    }
}