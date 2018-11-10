package com.example.naufa.matchSchedule.Api.Presenter

import com.example.naufa.matchSchedule.Api.ApiRepository
import com.example.naufa.matchSchedule.Api.SportAPI
import com.example.naufa.matchSchedule.Entity.Match
import com.example.naufa.matchSchedule.Entity.MatchResponse
import com.example.naufa.matchSchedule.MVP.MatchView
import com.example.naufa.matchSchedule.util.TestContextProvider
import com.google.gson.Gson
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MatchSearchPresenterTest {


    @Mock
    private
    lateinit var view: MatchView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: MatchSearchPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MatchSearchPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getMatch() {

        val matchEvent: MutableList<Match> = mutableListOf()
        val response = MatchResponse(matchEvent)
        val id  = "4328"

        Mockito.`when`(
            gson.fromJson(
                apiRepository
                    .doRequest(SportAPI.getLastMatch(id)), MatchResponse::class.java
            )
        )
            .thenReturn(response)
       presenter.getMatch(id)

        Mockito.verify(view).isLoading()
        Mockito.verify(view).showMatch(matchEvent)
        Mockito.verify(view).stopLoading()
    }
}