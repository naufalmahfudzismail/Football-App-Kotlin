package com.example.naufa.matchSchedule.Api

import com.example.naufa.matchSchedule.Entity.Match
import com.example.naufa.matchSchedule.Entity.MatchResponse
import com.example.naufa.matchSchedule.util.MatchView
import com.example.naufa.matchSchedule.util.TeamView
import com.example.naufa.matchSchedule.util.TestContextProvider
import com.google.gson.Gson
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DetailMatchPresenterTesting {

    @Mock
    private
    lateinit var matchView: MatchView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: MatchDetailPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MatchDetailPresenter(matchView, apiRepository, gson, TestContextProvider())
    }


    @Test
    fun getDetailMatch() {

        val teams: MutableList<Match> = mutableListOf()
        val response = MatchResponse(teams)
        val id = "1234"

        Mockito.`when`(gson.fromJson(apiRepository
            .doRequest(SportAPI.getMatch(id)),
            MatchResponse::class.java
        )).thenReturn(response)

        presenter.getDetailMatch(id)

        Mockito.verify(matchView).isLoading()
        Mockito.verify(matchView).showMatch(teams)
        Mockito.verify(matchView).stopLoading()
    }
}