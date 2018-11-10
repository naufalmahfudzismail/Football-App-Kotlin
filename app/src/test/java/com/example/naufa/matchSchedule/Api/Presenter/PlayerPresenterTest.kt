package com.example.naufa.matchSchedule.Api.Presenter

import com.example.naufa.matchSchedule.Api.ApiRepository
import com.example.naufa.matchSchedule.Api.SportAPI
import com.example.naufa.matchSchedule.Entity.Player
import com.example.naufa.matchSchedule.Entity.PlayerResponse
import com.example.naufa.matchSchedule.Entity.Team
import com.example.naufa.matchSchedule.Entity.TeamResponse
import com.example.naufa.matchSchedule.MVP.PlayerView
import com.example.naufa.matchSchedule.MVP.TeamListView
import com.example.naufa.matchSchedule.util.TestContextProvider
import com.google.gson.Gson
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class PlayerPresenterTest {

    @Mock
    private
    lateinit var playView: PlayerView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: PlayerPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = PlayerPresenter(playView, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getPlayerList() {

        val players: MutableList<Player> = mutableListOf()
        val response = PlayerResponse(players)
        val id = "1234"

        Mockito.`when`(
            gson.fromJson(
                apiRepository
                    .doRequest(SportAPI.getPlayerTeam(id)),
                PlayerResponse::class.java
            )
        ).thenReturn(response)

        presenter.getPlayerList(id)

        Mockito.verify(playView).isLoading()
        Mockito.verify(playView).showPlayer(players)
        Mockito.verify(playView).stopLoading()
    }
}