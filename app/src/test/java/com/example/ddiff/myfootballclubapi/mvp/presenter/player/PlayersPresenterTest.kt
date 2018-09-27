package com.example.ddiff.myfootballclubapi.mvp.presenter.player

import com.example.ddiff.myfootballclubapi.mvp.model.PlayerModel
import com.example.ddiff.myfootballclubapi.mvp.model.PlayerResponse
import com.example.ddiff.myfootballclubapi.network.ApiService
import com.example.ddiff.myfootballclubapi.utils.SchedulerProvider
import com.example.ddiff.myfootballclubapi.utils.SchedulerProviderTest
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class PlayersPresenterTest {
    @Mock
    lateinit var apiService: ApiService

    @Mock
    lateinit var playerView: PlayersContract.View
    lateinit var presenter: PlayersPresenter

    lateinit var playerResponse: PlayerResponse

    lateinit var players: Observable<PlayerResponse>

    private val player = mutableListOf<PlayerModel>()
    lateinit var scheduler: SchedulerProvider

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        scheduler = SchedulerProviderTest()
        playerResponse = PlayerResponse(player, player)
        players = Observable.just(playerResponse)
        presenter = PlayersPresenter(playerView, apiService, scheduler)
        Mockito.`when`(apiService.loadAllPlayers("133604")).thenReturn(players)
    }

    @Test
    fun getPlayers() {
        presenter.getPlayers("133604")
        Mockito.verify(playerView).onLoadPlayers(player)
    }
}