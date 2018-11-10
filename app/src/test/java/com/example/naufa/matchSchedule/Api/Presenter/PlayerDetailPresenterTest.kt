package com.example.naufa.matchSchedule.Api.Presenter

import com.example.naufa.matchSchedule.Api.ApiRepository
import com.example.naufa.matchSchedule.Api.SportAPI
import com.example.naufa.matchSchedule.Entity.Player
import com.example.naufa.matchSchedule.Entity.PlayerDetail
import com.example.naufa.matchSchedule.Entity.PlayerDetailResponse
import com.example.naufa.matchSchedule.Entity.PlayerResponse
import com.example.naufa.matchSchedule.MVP.PlayerDetailView
import com.example.naufa.matchSchedule.MVP.PlayerView
import com.example.naufa.matchSchedule.util.TestContextProvider
import com.google.gson.Gson
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class PlayerDetailPresenterTest {

    @Mock
    private
    lateinit var playView: PlayerDetailView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: PlayerDetailPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = PlayerDetailPresenter(playView, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getPlayerList() {

        val players: MutableList<PlayerDetail> = mutableListOf()
        val response = PlayerDetailResponse(players)
        val id = "1234"

        Mockito.`when`(
            gson.fromJson(
                apiRepository
                    .doRequest(SportAPI.getPlayerDetail(id)),
                PlayerDetailResponse::class.java
            )
        ).thenReturn(response)

        presenter.getPlayerList(id)

        Mockito.verify(playView).isLoading()
        Mockito.verify(playView).showPlayerDetail(players)
        Mockito.verify(playView).stopLoading()
    }
}