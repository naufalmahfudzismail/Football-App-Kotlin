package com.example.naufal.football.Api.Presenter

import com.example.naufal.football.Api.ApiRepository
import com.example.naufal.football.Api.SportAPI
import com.example.naufal.football.Entity.Player
import com.example.naufal.football.Entity.PlayerResponse
import com.example.naufal.football.Api.ModelView.PlayerView
import com.example.naufal.football.Util.TestContextProvider
import com.google.gson.Gson
import org.junit.Test

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